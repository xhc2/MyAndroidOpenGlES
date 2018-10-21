#include <jni.h>
#include <string>
#include <my_log.h>
#include "GLES2/gl2ext.h"
#include "GLES2/gl2.h"
#include "myopengl.h"

MyOpenGl *mygl;

extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_surfaceCreate(JNIEnv *env, jclass type) {
    LOGE("%d ", mygl);
    mygl->createSurface();
}

extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_drawFrame(JNIEnv *env, jclass type) {
    mygl->drawFrame();
}

extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_surfaceChanged(JNIEnv *env, jclass type,
                                                                        jint width, jint height) {
    mygl->surfaceChange(width, height);
}




extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_initOpenGl(JNIEnv *env, jclass type,
                                                                    jstring vs_, jstring fs_) {
    const char *vs = env->GetStringUTFChars(vs_, 0);
    const char *fs = env->GetStringUTFChars(fs_, 0);
    mygl = new MyOpenGl(vs , fs);
    env->ReleaseStringUTFChars(vs_, vs);
    env->ReleaseStringUTFChars(fs_, fs);
}