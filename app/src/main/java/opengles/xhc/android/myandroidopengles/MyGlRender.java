package opengles.xhc.android.myandroidopengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.io.FileInputStream;
import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
public class MyGlRender implements GLSurfaceView.Renderer {

    ByteBuffer y ;
    ByteBuffer u ;
    ByteBuffer v ;

    public MyGlRender(Context context) {
        String vs = TextResourceReader.readTextFileFromResource(context , R.raw.yuv_vertex_shader);
        String fs = TextResourceReader.readTextFileFromResource(context , R.raw.yuv_frg_shader);
//        final String vs = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertext_shader_java_1);
//        final String fs = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader_java_1);
        OpenGlNative.initOpenGl(vs, fs, 640, 360, 0);
//        try {
//            int width = 640 ;
//            int height = 360 ;
//            int yLen = width * height ;
//            int yuvSize = yLen * 3 /2 ;
//            byte[] buffer = new byte[yuvSize];
//            FileInputStream fis = new FileInputStream("sdcard/FFmpeg/oneframe.yuv");
//            fis.read(buffer);
//            byte[] byteY = new byte[yLen];
//            byte[] byteU = new byte[yLen / 4];
//            byte[] byteV = new byte[yLen / 4];
//            System.arraycopy(buffer, 0, byteY, 0, yLen);
//            System.arraycopy(buffer, yLen, byteU, 0, yLen / 4);
//            System.arraycopy(buffer, yLen * 5 / 4, byteV, 0, yLen / 4);
//            y = ByteBuffer.wrap(byteY);
//            u = ByteBuffer.wrap(byteU);
//            v = ByteBuffer.wrap(byteV);
////            OpenGlNative.setRender(byteY , byteU , byteV);
//        } catch (Exception e) {
//            Log.e("xhc", " message " + e.getMessage());
//            e.printStackTrace();
//        }
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        OpenGlNative.surfaceCreate();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        OpenGlNative.surfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        OpenGlNative.drawFrame();
//        int width = 640 ;
//        int height = 360;
//        glClearColor(0.0f , 0.0f , 1.0f , 1.0f);
//    glClear(GL_COLOR_BUFFER_BIT );
//        glClear(GL_COLOR_BUFFER_BIT);
//        glUseProgram(3);
//
//        glActiveTexture(GL_TEXTURE0);
//        glBindTexture(GL_TEXTURE_2D, 1);
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width, height, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, y);
//        glUniform1i(2, 0);
//
//
//        glActiveTexture(GL_TEXTURE1);
//        glBindTexture(GL_TEXTURE_2D, 2);
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width / 2 , height / 2, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, u);
//        glUniform1i(1, 1);
//
//        glActiveTexture(GL_TEXTURE2);
//        glBindTexture(GL_TEXTURE_2D, 3);
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width / 2, height / 2, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, v);
//        glUniform1i(0, 2);
//
//        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
    }
}