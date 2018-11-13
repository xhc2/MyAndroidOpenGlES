package opengles.xhc.android.myandroidopengles.javaopengl;

import android.content.Context;
import android.graphics.Shader;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import opengles.xhc.android.myandroidopengles.R;

import static android.opengl.GLES20.*;

public class AirHockeyRender implements GLSurfaceView.Renderer {


    private final static int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;
    private int program ;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation ;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation ;
    private Context context;
    public AirHockeyRender(Context context) {
        this.context = context;
        float[] tableVertices = {
                0f, 0f,
                0f, 14f,
                9f, 14f,
                9f, 0f
        };
        float[] tableVerticesWithTriangles = {
                //trangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                //trangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f ,

                // line 1
                -0.5f , 0f ,
                0.5f , 0f ,
                // mallets
                0f , -0.25f ,
                0f , 0.25f
        };
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f , 0.0f ,0.0f,0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context , R.raw.simple_vertext_shader_java_1);
        String frgShaderSource = TextResourceReader.readTextFileFromResource(context , R.raw.simple_fragment_shader_java_1);
        Log.e("xhc", vertexShaderSource +"\n\n\n"+frgShaderSource);
        int vertextShader = ShaderHelper.compileVertextShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(frgShaderSource);
        program = ShaderHelper.linkProgram(vertextShader , fragmentShader);
        ShaderHelper.validatePrograme(program);
        glUseProgram(program);

        uColorLocation = glGetUniformLocation(program , U_COLOR);
        aPositionLocation = glGetAttribLocation(program , A_POSITION);
        vertexData.position(0);
        /**
         * aPositionLocation 传递属性位置
         * POSITION_COMPONENT_COUNT 每个属性的计数
         * GL_FLOAT 写入的数据类型
         * normalized 只有使用整型的时候才有意义
         * stride 这个只有当一个数组存多于一个属性时，他才有意义。目前只有一个位置属性
         * vertexData 这是告诉opengl去哪里读取数据。
         */
        glVertexAttribPointer(aPositionLocation , POSITION_COMPONENT_COUNT , GL_FLOAT , false , 0 , vertexData);
        /**
         * 使能定点数组
         */
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        /**
         * 使用 glUniform4f 更新着色器中的u_color的值
         */
        glUniform4f(uColorLocation , 1.0f , 1.0f , 1.0f , 1.0f);
        //画桌子
        glDrawArrays(GL_TRIANGLES , 0 , 6);

        /**
         * 绘制分割线
         */
        glUniform4f(uColorLocation , 1.0f , 0.0f , 0.0f , 1.0f);
        glDrawArrays(GL_LINES , 6 , 2);
        /**
         * 绘制点
         */
        glUniform4f(uColorLocation , 0.0f , 0.0f , 1.0f , 1.0f);
        glDrawArrays(GL_POINTS , 8 , 1);
        /**
         * 绘制点
         */
        glUniform4f(uColorLocation , 0.0f , 0.0f , 1.0f , 1.0f);
        glDrawArrays(GL_POINTS , 9 , 1);

    }
}
