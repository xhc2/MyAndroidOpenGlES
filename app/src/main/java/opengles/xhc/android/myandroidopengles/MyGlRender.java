package opengles.xhc.android.myandroidopengles;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGlRender implements GLSurfaceView.Renderer{


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        OpenGlNative.surfaceCreate();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        OpenGlNative.surfaceChanged(width , height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        OpenGlNative.drawFrame();
    }
}