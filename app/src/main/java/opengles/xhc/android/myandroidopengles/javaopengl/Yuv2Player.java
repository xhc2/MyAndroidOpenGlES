package opengles.xhc.android.myandroidopengles.javaopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import opengles.xhc.android.myandroidopengles.R;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.*;
import static android.opengl.GLES30.GL_RED;

public class Yuv2Player implements GLSurfaceView.Renderer {

    private Context context;
    private final FloatBuffer vertexData;
    private final FloatBuffer texData;
    //    private final FloatBuffer texData;
    private static final int BYTES_PER_FLOAT = 4;
    private int program;
    private int aPositionL;
    private int aTextureCoordinatesL;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONNET_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONNET_COUNT) * BYTES_PER_FLOAT;
    private int textureYL;
    private int textureUL;
    private int textureVL;
    private int textureYid;
    private int textureUid;
    private int textureVid;

    private int width = 640;
    private int height = 360;
    private FileInputStream fis;
    private byte[] byteY;
    private byte[] byteU;
    private byte[] byteV;

    private ByteBuffer bufferY;
    private ByteBuffer bufferU;
    private ByteBuffer bufferV;

    public Yuv2Player(Context context) {
        this.context = context;
        int yLen = width * height;
        int yuvSize = yLen * 3 / 2;
        float vertexVertices[] = {
                1.0f, 1.0f,
                -1.0f, 1.0f,
                1.0f,  -1.0f,
                -1.0f,  -1.0f,
        };
        float textureVertices[] = {
                0.0f,  1.0f,
                1.0f,  1.0f,
                0.0f,  0.0f,
                1.0f,  0.0f,
        };
        vertexData = ByteBuffer.allocateDirect(vertexVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(vertexVertices);
        vertexData.position(0);

        texData = ByteBuffer.allocateDirect(textureVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        texData.put(textureVertices);
        texData.position(0);


        Log.e("xhc", " yuvSize " + yuvSize + " yLen " + yLen);
        try {
            byte[] buffer = new byte[yuvSize];
            fis = new FileInputStream("sdcard/FFmpeg/oneframe.yuv");
            fis.read(buffer);
            byteY = new byte[yLen];
            byteU = new byte[yLen / 4];
            byteV = new byte[yLen / 4];
            System.arraycopy(buffer, 0, byteY, 0, yLen);
            System.arraycopy(buffer, yLen, byteU, 0, yLen / 4);
            System.arraycopy(buffer, yLen * 5 / 4, byteV, 0, yLen / 4);
            bufferY = ByteBuffer.wrap(byteY);
            bufferU = ByteBuffer.wrap(byteU);
            bufferV = ByteBuffer.wrap(byteV);
//            bufferY.put(byteY);
//            bufferU.put(byteU);
//            bufferV.put(byteV);
        } catch (Exception e) {
            Log.e("xhc", " message " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.yuv_vertex_shader);
        String frgShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.yuv_frg_shader);
        int vertextShader = ShaderHelper.compileVertextShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(frgShaderSource);
        program = ShaderHelper.linkProgram(vertextShader, fragmentShader);
        ShaderHelper.validatePrograme(program);
        glUseProgram(program);

        textureYL = glGetUniformLocation(program, "yTexture");
        textureUL = glGetUniformLocation(program, "uTexture");
        textureVL = glGetUniformLocation(program, "vTexture");
        aPositionL = glGetAttribLocation(program, "aPosition");
        aTextureCoordinatesL = glGetAttribLocation(program, "aTexCoord");
        vertexData.position(0);

        int[] textures = TextureHelper.initYuvTexture();
        if (textures == null) {
            return;
        }
        textureYid = textures[0];
        textureUid = textures[1];
        textureVid = textures[2];

        glVertexAttribPointer(aPositionL, 2, GL_FLOAT,false , 0, vertexData);
        glEnableVertexAttribArray(aPositionL);

        glVertexAttribPointer(aTextureCoordinatesL, 2, GL_FLOAT, false, 0, texData);
        glEnableVertexAttribArray(aTextureCoordinatesL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0 , 0 , width , height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glUseProgram(program);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureYid);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width, height, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, bufferY);
        glUniform1i(textureYL, 0);

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, textureUid);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width/2, height/2, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, bufferU);
        glUniform1i(textureUL, 1);

        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, textureVid);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width/2, height/2, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, bufferV);
        glUniform1i(textureVL, 2);

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }
}
