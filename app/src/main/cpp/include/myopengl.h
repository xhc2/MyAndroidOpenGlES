//
// Created by Administrator on 2018/10/19/019.
//

#ifndef MYANDROIDOPENGLES_MYOPENGL_H
#define MYANDROIDOPENGLES_MYOPENGL_H


#include "MyOpenGlUtils.h"
#include "MyThread.h"
#include <my_log.h>

class MyOpenGl : public MyThread{

private :
    char* vs ;
    char* fs ;
    char *path ;
    int width ;
    int height;
    int yuvType;
    void checkGlError(const char *op);
    GLuint program;
    GLuint shaderFrg;
    GLuint shaderVer;
    GLint textureYL ;
    GLint textureUL ;
    GLint textureVL ;
    GLint aPositionL ;
    GLint aTextureCoordinatesL ;

    GLuint textureYid;
    GLuint textureUid;
    GLuint textureVid;

    unsigned char *y;
    unsigned char *u;
    unsigned char *v;

    FILE *yuvF;

    void myDraw();

    bool initShaderFlag ;
public :
    virtual void run() ;
    MyOpenGl(const char* path , const char* vs , const char* fs , int width , int height , int yuvType);
    ~MyOpenGl();
    void createSurface();
    void initShader();
    void surfaceChange(int width , int height);
    void drawFrame();
    void showYuv(unsigned char *tempY , unsigned char *tempU , unsigned char *tempV );
};

#endif //MYANDROIDOPENGLES_MYOPENGL_H
