package opengles.xhc.android.myandroidopengles;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {


    private MyGlSurfaceView surfaceView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyGlRender render = new MyGlRender();
        surfaceView = findViewById(R.id.surfaceview);
        surfaceView.setEGLContextClientVersion(2);
        surfaceView.setRenderer(render);
        
    }
}
