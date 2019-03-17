package opengles.xhc.android.myandroidopengles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import opengles.xhc.android.myandroidopengles.javaopengl.TextureRender;

/**
 * 渲染rgb图片
 */
public class RgbRenderActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgb_render);
        glSurfaceView = findViewById(R.id.gl_surface_view);
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo cgi = am.getDeviceConfigurationInfo();
        //验证是否支持opengles 2.0
        if (cgi.reqGlEsVersion > 0x20000) {
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(new TextureRender(this));
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY /*自己请求刷新*/); // RENDERMODE_CONTINUOUSLY 一直不断刷新
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