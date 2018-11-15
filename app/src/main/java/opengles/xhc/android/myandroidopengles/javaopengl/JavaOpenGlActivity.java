package opengles.xhc.android.myandroidopengles.javaopengl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import opengles.xhc.android.myandroidopengles.R;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.FileInputStream;

public class JavaOpenGlActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private byte[] byteY;
    private byte[] byteU;
    private byte[] byteV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        glSurfaceView = new GLSurfaceView(this);
        setContentView(/*R.layout.activity_java_open_gl*/glSurfaceView);
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo cgi = am.getDeviceConfigurationInfo();

        int width = 640 ;
        int height = 360 ;
        int yLen = width * height ;
        int yuvSize = yLen * 3 /2 ;
        try {
            byte[] buffer = new byte[yuvSize];
            FileInputStream fis = new FileInputStream("sdcard/FFmpeg/oneframe.yuv");
            fis.read(buffer);
            byteY = new byte[yLen];
            byteU = new byte[yLen / 4];
            byteV = new byte[yLen / 4];
            System.arraycopy(buffer, 0, byteY, 0, yLen);
            System.arraycopy(buffer, yLen, byteU, 0, yLen / 4);
            System.arraycopy(buffer, yLen * 5 / 4, byteV, 0, yLen / 4);
        } catch (Exception e) {
            Log.e("xhc", " message " + e.getMessage());
            e.printStackTrace();
        }

//        WlRender render = new WlRender(this);
        if(cgi.reqGlEsVersion > 0x20000){
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(/*render*/new Yuv2Player(this));
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//            render.setYUVRenderData(640,360 , byteY , byteU , byteV);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}
