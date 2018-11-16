package opengles.xhc.android.myandroidopengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.FileInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {



//    class MyGLRenderer implements GLSurfaceView.Renderer {
//        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//            final String vs = TextResourceReader.readTextFileFromResource(MainActivity.this, R.raw.simple_vertext_shader_java_1);
//            final String fs = TextResourceReader.readTextFileFromResource(MainActivity.this, R.raw.simple_fragment_shader_java_1);
//            OpenGlNative.initOpenGl(vs, fs, 640, 360, 0);
//        }
//        public void onSurfaceChanged(GL10 gl, int width, int height) {
//            OpenGlNative.surfaceChanged(width,height);
//        }
//        public void onDrawFrame(GL10 gl) {
//            OpenGlNative.drawFrame();
//        }
//    }
//    class MyGLSurface extends GLSurfaceView {
//        MyGLRenderer mRenderer;
//        public MyGLSurface(Context context) {
//            super(context);
//            setEGLContextClientVersion(2);
//            mRenderer=new MyGLRenderer();
//            setRenderer(mRenderer);
//        }
//    }
//    MyGLSurface mView;
    private MyGlSurfaceView surfaceView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
//        mView=new MyGLSurface(getApplication());
//        setContentView(mView);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceview);
        MyGlRender render = new MyGlRender(this);
        surfaceView.setEGLContextClientVersion(2);
        surfaceView.setRenderer(render);
        try {
            int width = 640 ;
            int height = 360 ;
            int yLen = width * height ;
            int yuvSize = yLen * 3 /2 ;
            byte[] buffer = new byte[yuvSize];
            FileInputStream fis = new FileInputStream("sdcard/FFmpeg/oneframe.yuv");
            fis.read(buffer);
            byte[] byteY = new byte[yLen];
            byte[] byteU = new byte[yLen / 4];
            byte[] byteV = new byte[yLen / 4];
            System.arraycopy(buffer, 0, byteY, 0, yLen);
            System.arraycopy(buffer, yLen, byteU, 0, yLen / 4);
            System.arraycopy(buffer, yLen * 5 / 4, byteV, 0, yLen / 4);
//            OpenGlNative.setRender(byteY , byteU , byteV);
        } catch (Exception e) {
            Log.e("xhc", " message " + e.getMessage());
            e.printStackTrace();
        }

        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        surfaceView.onPause();
    }

}
