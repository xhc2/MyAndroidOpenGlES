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


    public MyGlRender(Context context , String path , int w , int h ) {
        String vs = TextResourceReader.readTextFileFromResource(context , R.raw.yuv_vertex_shader);
        String fs = TextResourceReader.readTextFileFromResource(context , R.raw.yuv_frg_shader);
        OpenGlNative.initOpenGl(path , vs, fs,  w,  h, 0);
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
    }
}