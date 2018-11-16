//
// Created by Administrator on 2018/10/19/019.
//

#ifndef MYANDROIDOPENGLES_MYOPENGLUTILS_H
#define MYANDROIDOPENGLES_MYOPENGLUTILS_H

#include "GLES2/gl2ext.h"
#include "GLES2/gl2.h"

GLuint compileShader(GLenum shaderType , const char *shaderCode);

GLuint createProgram(GLuint vsShader , GLuint fsShader);
void checkGlError(const char *op);

GLuint* initTextTure();
//GLuint validateProgram(GLuint program);
#endif //MYANDROIDOPENGLES_MYOPENGLUTILS_H
