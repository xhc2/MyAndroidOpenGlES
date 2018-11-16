package opengles.xhc.android.myandroidopengles;

public class OpenGlNative {
    static {
        System.loadLibrary("native-lib");
    }

    public static native void initOpenGl(String vs , String fs , int width , int height , int yuvType);
    public static native void surfaceCreate();
    public static native void surfaceChanged(int width , int height);
    public static native void drawFrame();
    public static native void setRender(byte[] y , byte[] u , byte[] v);

}
