

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

GLuint* initTextTure(){
    GLuint *textures = (GLuint *)malloc(3 * sizeof(int));
    glGenTextures(3 ,textures); //3是创建三个纹理对象的意思

    if(textures[0] == 0 || textures[1] == 0 || textures[2] == 0){
        LOGE( "cant open opengl texture object ！");
        return NULL;
    }
    for(int i = 0 ;i < 3 ; ++ i){
        glBindTexture(GL_TEXTURE_2D , textures[i]);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }
    return textures;
}















