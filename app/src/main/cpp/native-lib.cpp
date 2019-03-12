#include <jni.h>
#include <string>
#include <my_log.h>
#include "GLES2/gl2ext.h"
#include "GLES2/gl2.h"
#include "myopengl.h"
#include "opengl_table.h"

MyOpenGl *mygl = NULL;
extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_surfaceCreate(JNIEnv *env, jclass type) {
    mygl->createSurface();
//     surfaceCreate();
}

extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_drawFrame(JNIEnv *env, jclass type) {
    mygl->drawFrame();

//     drawFrame();
}

extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_surfaceChanged(JNIEnv *env, jclass type,
                                                                        jint width, jint height) {
    mygl->surfaceChange(width, height);
     viewChange(width ,height);
}

extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_setRender(JNIEnv *env, jclass type,
                                                                   jbyteArray y_, jbyteArray u_,
                                                                   jbyteArray v_) {
    jbyte *y = env->GetByteArrayElements(y_, NULL);
    jbyte *u = env->GetByteArrayElements(u_, NULL);
    jbyte *v = env->GetByteArrayElements(v_, NULL);

    mygl->showYuv((unsigned char*)y ,(unsigned char*) u ,(unsigned char*) v);

    env->ReleaseByteArrayElements(y_, y, 0);
    env->ReleaseByteArrayElements(u_, u, 0);
    env->ReleaseByteArrayElements(v_, v, 0);
}


extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_initOpenGl(JNIEnv *env, jclass type,
                                                                    jstring path_, jstring vs_,
                                                                    jstring fs_, jint width,
                                                                    jint height, jint yuvType) {
    const char *path = env->GetStringUTFChars(path_, 0);
    const char *vs = env->GetStringUTFChars(vs_, 0);
    const char *fs = env->GetStringUTFChars(fs_, 0);

    // TODO
    mygl = new MyOpenGl(path , vs , fs , width , height , yuvType);
    env->ReleaseStringUTFChars(path_, path);
    env->ReleaseStringUTFChars(vs_, vs);
    env->ReleaseStringUTFChars(fs_, fs);
}extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_renderDestroy(JNIEnv *env, jclass type) {

    // TODO
    if(mygl != NULL){
        delete mygl;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_opengles_xhc_android_myandroidopengles_OpenGlNative_initShader(JNIEnv *env, jclass type) {
    if(mygl != NULL){
        mygl->initShader();
    }
}