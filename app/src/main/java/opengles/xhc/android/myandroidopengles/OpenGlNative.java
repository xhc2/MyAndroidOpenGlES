package opengles.xhc.android.myandroidopengles;

public class OpenGlNative {
    static {
        System.loadLibrary("native-lib");
    }

    public static native void initOpenGl(String vs , String fs);
    public static native void surfaceCreate();
    public static native void surfaceChanged(int width , int height);
    public static native void drawFrame();

}
