package opengles.xhc.android.myandroidopengles;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import opengles.xhc.android.myandroidopengles.javaopengl.ShaderHelper;
import opengles.xhc.android.myandroidopengles.javaopengl.TextResourceReader;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class TrangleRender implements GLSurfaceView.Renderer {

    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer trangleData;
    private Context context;
    private int program;
    private int positonLocation ;
    private final static int POSITION_COMPONENT_COUNT = 2;
    public TrangleRender(Context context){
        this.context = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        float[] trangle = {
                //x, y
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,
        };

        trangleData = ByteBuffer.allocateDirect(trangle.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        trangleData.put(trangle);

        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context , R.raw.triangle_vs);
        String frgShaderSource = TextResourceReader.readTextFileFromResource(context , R.raw.triangle_fs);
        int vertextShader = ShaderHelper.compileVertextShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(frgShaderSource);
        program = ShaderHelper.linkProgram(vertextShader , fragmentShader);
        ShaderHelper.validatePrograme(program);
        glUseProgram(program);
        positonLocation = glGetAttribLocation(program , "a_position");
        trangleData.position(0);
        glVertexAttribPointer(positonLocation , POSITION_COMPONENT_COUNT ,   GL_FLOAT , false , 0 , trangleData);
        glEnableVertexAttribArray(positonLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
}
