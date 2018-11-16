package opengles.xhc.android.myandroidopengles.javaopengl;

import android.content.Context;
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
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LUMINANCE;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glTexSubImage2D;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.*;
import static android.opengl.GLES30.GL_RED;

public class YuvPlayerRender implements GLSurfaceView.Renderer {

    private Context context;
    private final FloatBuffer vertexData;
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

    public YuvPlayerRender(Context context) {
        this.context = context;
        int yLen = width * height;
        int yuvSize = yLen * 3 / 2;
        float[] tableVerticesTexture = {
                //x, y , s , t
                0f, 0f, 0.5f, 0.5f,
                1f, 1f, 0f, 1f,
                -1f, 1f, 1f, 1f,
                -1f, -1f, 1f, 0f,
                1f, -1f, 0f, 0f,
                1f, 1f, 0f, 1f,
        };

        vertexData = ByteBuffer.allocateDirect(tableVerticesTexture.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesTexture);

//        bufferY = ByteBuffer.allocateDirect(yLen).order(ByteOrder.nativeOrder());
//        bufferU = ByteBuffer.allocateDirect(yLen / 4).order(ByteOrder.nativeOrder());
//        bufferV = ByteBuffer.allocateDirect(yLen / 4).order(ByteOrder.nativeOrder());

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
            bufferY.wrap(byteY);
            bufferU.wrap(byteU);
            bufferV.wrap(byteV);
        } catch (Exception e) {
            Log.e("xhc", " message " + e.getMessage());
            e.printStackTrace();
        }

//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream("sdcard/FFmpeg/testonFrame.yuv");
//            fos.write(byteY);
//            fos.write(byteU);
//            fos.write(byteV);
//            fos.flush();
//            fos.close();
//        } catch (Exception e) {
//            Log.e("xhc" , " message "+e.getMessage());
//            e.printStackTrace();
//        }
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

        glUniform1i(textureYL, 0);
        glUniform1i(textureUL, 1);
        glUniform1i(textureVL, 2);

        int[] textures = TextureHelper.initYuvTexture( );
        if (textures == null) {
            return;
        }
        textureYid = textures[0];
        textureUid = textures[1];
        textureVid = textures[2];

        setVertexAttribPointer(
                0,
                aPositionL,
                POSITION_COMPONENT_COUNT,
                STRIDE);

        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                aTextureCoordinatesL,
                TEXTURE_COORDINATES_COMPONNET_COUNT,
                STRIDE);



    }

    private void setVertexAttribPointer(int dataOffset, int attributeLocation, int componetCount, int stride) {
        vertexData.position(dataOffset);
        glEnableVertexAttribArray(attributeLocation);
        glVertexAttribPointer(attributeLocation, componetCount, GL_FLOAT, false, stride, vertexData);
        vertexData.position(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUseProgram(program);


        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureYid);
        glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_LUMINANCE,
                GL_UNSIGNED_BYTE,
                bufferY);


        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, textureUid);
        glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width / 2, height / 2, GL_LUMINANCE,
                GL_UNSIGNED_BYTE,
                bufferU);


        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, textureVid);
        glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width / 2, height / 2, GL_LUMINANCE,
                GL_UNSIGNED_BYTE,
                bufferV);


        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
//        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
    }
}
