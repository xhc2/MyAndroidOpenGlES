//
// Created by Administrator on 2018/10/19/019.
//

#ifndef MYANDROIDOPENGLES_MYOPENGL_H
#define MYANDROIDOPENGLES_MYOPENGL_H

#include "GLES2/gl2ext.h"
#include "GLES2/gl2.h"

class MyOpenGl{

private :
    char* vs ;
    char* fs ;
public :
    MyOpenGl(const char* vs , const char* fs);
    ~MyOpenGl();
    void createSurface();
    void surfaceChange(int width , int height);
    void drawFrame();
    void drawTrangle();
    void initTrangle();

};

#endif //MYANDROIDOPENGLES_MYOPENGL_H
