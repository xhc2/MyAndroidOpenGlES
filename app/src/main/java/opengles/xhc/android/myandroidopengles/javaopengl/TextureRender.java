package opengles.xhc.android.myandroidopengles.javaopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import opengles.xhc.android.myandroidopengles.R;

import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.*;

public class TextureRender implements GLSurfaceView.Renderer {

    private Context context;
    private final FloatBuffer vertexData;
    private static final int BYTES_PER_FLOAT = 4;
    private int program;
    private int uTextureUnitL;
    private int aPositionL;
    private int aTextureCoordinatesL;
    private int texttureId;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONNET_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONNET_COUNT) * BYTES_PER_FLOAT;

    public TextureRender(Context context) {
        this.context = context;
        float[] tableVerticesTexture = {
                //x, y , s , t
                0f, 0f, 0.5f, 0.5f,
                -1f, -1f, 0f, 1f,
                1f, -1f, 1f, 1f,
                1f, 1f, 1f, 0f,
                -1f, 1f, 0f, 0f,
                -1f, -1f, 0f, 1f,
        };
        vertexData = ByteBuffer.allocateDirect(tableVerticesTexture.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesTexture);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.texture_vertext_shader_java_1);
        String frgShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.texture_frament_shader_java_1);

        int vertextShader = ShaderHelper.compileVertextShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(frgShaderSource);
        program = ShaderHelper.linkProgram(vertextShader, fragmentShader);
        ShaderHelper.validatePrograme(program);
        glUseProgram(program);

        uTextureUnitL = glGetUniformLocation(program, "u_TextureUnit");
        aPositionL = glGetAttribLocation(program, "a_Position");
        aTextureCoordinatesL = glGetAttribLocation(program, "a_TextureCoordinates");
        vertexData.position(0);
        texttureId = TextureHelper.loadTexture(context, R.mipmap.air_hockey_surface);
        Log.e("xhc", " textture " + texttureId);
        setVertexAttribPointer(0,
                aPositionL,
                POSITION_COMPONENT_COUNT,
                STRIDE);

        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                aTextureCoordinatesL,
                TEXTURE_COORDINATES_COMPONNET_COUNT,
                STRIDE);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
    }

    private void setVertexAttribPointer(int dataOffset, int attributeLocation, int componetCount, int stride) {
        vertexData.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componetCount, GL_FLOAT, false, stride, vertexData);
        glEnableVertexAttribArray(attributeLocation);
        vertexData.position(0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUseProgram(program);

        //drawtexture
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texttureId);
        glUniform1i(uTextureUnitL, 0);



        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
