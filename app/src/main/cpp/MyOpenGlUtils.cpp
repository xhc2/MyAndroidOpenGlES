

#include <my_log.h>
#include "MyOpenGlUtils.h"

GLuint compileShader(GLenum shaderType , const char *shaderCode){
    /**
     * 传入shader种类来创建shader对象
     */
    GLuint shader = glCreateShader(shaderType);
    /**
     * 1.是代表一段代码。
     * 2.shaderCode 代码的地址
     */
    glShaderSource(shader  , 1 , &shaderCode , NULL);
    /**
     * 编译
     */
    glCompileShader(shader);
    GLint compileResult = GL_TRUE;
    /**
     * 获取编译状态
     */
    glGetShaderiv(shader , GL_COMPILE_STATUS , &compileResult);
    if(compileResult == GL_FALSE){
        char log[1024] = {0};
        GLsizei len = 0;
        /**
         * 获取编译错误日志
         * len用来记录错误日志用了多少空间
         */
        glGetShaderInfoLog(shader , 1024 , &len , log);
        LOGE("compile shader error %s ,\n shader code %s " , log , shaderCode);
        glDeleteShader(shader);
        shader = 0;
    }
    return shader;
}


GLuint createProgram(GLuint vsShader , GLuint fsShader){
    GLuint program = glCreateProgram();
    glAttachShader(program , vsShader);
    glAttachShader(program , fsShader);
    glLinkProgram(program);
    glDetachShader(program , vsShader);
    glDetachShader(program , fsShader);
    GLint result ;
    glGetProgramiv(program , GL_LINK_STATUS , &result);
    if(result == GL_FALSE){
        char log[1024] = {0};
        GLsizei len = 0;
        glGetProgramInfoLog(program , 1024 , &len  , log);
        LOGE("compile shader error %s ,\n  " , log);
        glDeleteProgram(program);
        program = 0;
    }
    return program;
}

















