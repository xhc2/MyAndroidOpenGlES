//
// Created by Administrator on 2018/10/19/019.
//

#ifndef MYANDROIDOPENGLES_MYOPENGL_H
#define MYANDROIDOPENGLES_MYOPENGL_H


#include "MyOpenGlUtils.h"
#include <my_log.h>

class MyOpenGl{

private :
    char* vs ;
    char* fs ;
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



public :

    MyOpenGl(const char* vs , const char* fs , int width , int height , int yuvType);
    ~MyOpenGl();
    void createSurface();
    void surfaceChange(int width , int height);
    void drawFrame();
    void showYuv(unsigned char *tempY , unsigned char *tempU , unsigned char *tempV );
};

#endif //MYANDROIDOPENGLES_MYOPENGL_H
