//
// Created by Administrator on 2018/10/19/019.
//

#include <my_log.h>
#include "myopengl.h"
#include "MyOpenGlUtils.h"

MyOpenGl::MyOpenGl(const char* vs , const char* fs){
    int vsLen = strlen(vs);
    int fsLen = strlen(fs);
    this->vs = (char *)malloc(vsLen);
    this->fs = (char *)malloc(fsLen);
    strcpy(this->vs ,  vs);
    strcpy(this->fs ,  fs);
}

void MyOpenGl::createSurface(){
    glClearColor(1.0f , 0.0f , 0.0f , 1.0f);
}

void MyOpenGl::surfaceChange(int width , int height){
    glViewport(0.0f , 0.0f , width , height);
}

void MyOpenGl::drawFrame(){
    glClear(GL_COLOR_BUFFER_BIT);
}

void MyOpenGl::initTrangle(){
    GLuint vboTrangle ;
    GLuint  programTrangle ;
    float data[] = {
            -0.2f , -0.2f , -0.6f , 1.0f ,
            0.2f , -0.2f , -0.6f , 1.0f ,
            0.0f , 0.2f , -0.6f , 1.0f ,
    };
    /**
     * vboTrangle用来保存一个opengl可以识别的显存一个内存块
     */
    glGenBuffers(1 , &vboTrangle);
    /**
     * 把GL_ARRAY_BUFFER设置成当前的vbo
     */
    glBindBuffer(GL_ARRAY_BUFFER , vboTrangle);
    /**
     * 设置当前要操作的是GL_ARRAY_BUFFER
     * sizeof(float) * 12 在gpu要开辟多大空间
     * data 数据地址
     * GL_STATIC_DRAW 标识放在显卡上就不修改了。
     * 这个函数运行完就是把数据从cpu 发送到gpu端了。
     */
    glBufferData(GL_ARRAY_BUFFER , sizeof(float) * 12 , data , GL_STATIC_DRAW);
    /**
     * 设置当前的GL_ARRAY_BUFFER为0。
     * 避免对之前设置好的buffer进行操作
     */
    glBindBuffer(GL_ARRAY_BUFFER , 0);
    GLuint vsShader = compileShader(GL_VERTEX_SHADER , vs);
    delete vs;
    GLuint fsShader = compileShader(GL_FRAGMENT_SHADER , vs);
    delete fs;
    programTrangle = createProgram(vsShader , fsShader);
    /**
     * 创建了后可以删除掉shader了。
     */
    glDeleteShader(vsShader);
    glDeleteShader(fsShader);

    GLint positionL , modelMatrixL , viewMatrixL , projectionMatrixL ;
    /*
     * 以下都可以理解成插槽的概念
     * glGetAttribLocation(programTrangle , "position")获取“position”的插槽
     * glGetUniformLocation(programTrangle , "ModelMatrix") 获取"ModelMatrix"的插槽
     * 并且attribute ， uniform，的插槽都是相互独立的。比如都是（0~7）
     */
    positionL = glGetAttribLocation(programTrangle , "position");
    modelMatrixL = glGetUniformLocation(programTrangle , "ModelMatrix");
    viewMatrixL = glGetUniformLocation(programTrangle , "ViewMatrix");
    projectionMatrixL = glGetUniformLocation(programTrangle , "ProjectionMatrix");


}

MyOpenGl::~MyOpenGl(){

}
