package opengles.xhc.android.myandroidopengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

public class MyGlSurfaceView extends GLSurfaceView implements SurfaceHolder.Callback {

    public MyGlSurfaceView(Context context) {
        this(context , null);
    }

    public MyGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String vs = TextResourceReader.readTextFileFromResource(context , R.raw.triangle_vs);
        String fs = TextResourceReader.readTextFileFromResource(context , R.raw.triangle_fs);
        OpenGlNative.initOpenGl(vs , fs);
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
    }
}
