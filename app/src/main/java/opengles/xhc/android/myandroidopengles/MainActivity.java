package opengles.xhc.android.myandroidopengles;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.FileInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private MyGlSurfaceView surfaceView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceview);

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        int width = intent.getIntExtra("w" , -1);
        int height = intent.getIntExtra("h" , -1);

        MyGlRender render = new MyGlRender(this , path , width , height);
        surfaceView.setEGLContextClientVersion(2);
        surfaceView.setRenderer(render);
//        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGlNative.initShader();
            }
        });

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
