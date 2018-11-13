//
// Created by Administrator on 2018/10/19/019.
//

#include <my_log.h>
#include "myopengl.h"
#include "MyOpenGlUtils.h"
#include "Matrix.h"

Matrix *mViewMatrix;
Matrix *mModelMatrix;
Matrix *mProjectionMatrix;
Matrix *mMVPMatrix;

GLfloat dataTriangle[] = {
        -0.2f , -0.2f , -0.6f , 1.0f ,
        0.2f , -0.2f , -0.6f , 1.0f ,
        0.0f , 0.2f , -0.6f , 1.0f ,
};

MyOpenGl::MyOpenGl(const char* vs , const char* fs){
    int vsLen = strlen(vs);
    int fsLen = strlen(fs);
    fsLen++;
    vsLen++;
    this->vs = (char *)malloc(vsLen);
    this->fs = (char *)malloc(fsLen);
    strcpy(this->vs ,  vs);
    strcpy(this->fs ,  fs);
    mModelMatrix = new Matrix();
    mMVPMatrix = new Matrix();
    initTrangle();
}

void MyOpenGl::createSurface(){
    glClearColor(1.0f , 0.0f , 0.0f , 1.0f);

    // Position the eye in front of the origin.
    float eyeX = 0.0f;
    float eyeY = 0.0f;
    float eyeZ = 1.5f;

    // We are looking at the origin
    float centerX = 0.0f;
    float centerY = 0.0f;
    float centerZ = 0.0f;

    // Set our up vector.
    float upX = 0.0f;
    float upY = 1.0f;
    float upZ = 0.0f;

    // Set the view matrix.
    mViewMatrix = Matrix::newLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
}

void MyOpenGl::surfaceChange(int width , int height){
    glViewport(0.0f , 0.0f , width , height);
    // Create a new perspective projection matrix. The height will stay the same
    // while the width will vary as per aspect ratio.
    float ratio = (float) width / height;
    float left = -ratio;
    float right = ratio;
    float bottom = -1.0f;
    float top = 1.0f;
    float near = 1.0f;
    float far = 2.0f;

    mProjectionMatrix = Matrix::newFrustum(left, right, bottom, top, near, far);
}

void MyOpenGl::drawFrame(){
    glClearColor(0.0f , 0.0f , 0.0f , 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    drawTrangle();
}

void MyOpenGl::initTrangle(){
    GLuint vboTrangle ;

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
    glBufferData(GL_ARRAY_BUFFER , sizeof(float) * 12 , dataTriangle , GL_STATIC_DRAW);
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


    /*
     * 以下都可以理解成插槽的概念
     * glGetAttribLocation(programTrangle , "position")获取“position”的插槽
     * glGetUniformLocation(programTrangle , "ModelMatrix") 获取"ModelMatrix"的插槽
     * 并且attribute ， uniform，的插槽都是相互独立的。比如都是（0~7）
     */
    positionL = glGetAttribLocation(programTrangle , "position");
    modelMatrixL = glGetUniformLocation(programTrangle , "ModelMatrix");
//    viewMatrixL = glGetUniformLocation(programTrangle , "ViewMatrix");
//    projectionMatrixL = glGetUniformLocation(programTrangle , "ProjectionMatrix");

}

 void MyOpenGl::checkGlError(const char *op) {
    for (GLint error = glGetError(); error; error = glGetError()) {
        LOGI("after %s() glError (0x%x)\n", op, error);
    }
}


void MyOpenGl::drawTrangle(){
    glUseProgram(programTrangle);
//    glUniformMatrix4fv(modelMatrixL , 1 , GL_FALSE ,);
    glVertexAttribPointer(
            (GLuint) positionL,
            3,
            GL_FLOAT,
            GL_FALSE,
            4 * 7,
            dataTriangle
    );
    glEnableVertexAttribArray((GLuint) positionL);
    // model * view
    mMVPMatrix->multiply(*mViewMatrix, *mModelMatrix);

    // model * view * projection
    mMVPMatrix->multiply(*mProjectionMatrix, *mMVPMatrix);
    glUniformMatrix4fv(modelMatrixL, 1, GL_FALSE, mMVPMatrix->mData);

    glDrawArrays(GL_TRIANGLES, 0, 3);
    checkGlError("glDrawArrays");
}


MyOpenGl::~MyOpenGl(){

}
