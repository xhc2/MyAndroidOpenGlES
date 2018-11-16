//
// Created by Administrator on 2018/11/15/015.
//
#include <my_log.h>
#include "opengl_table.h"
#include "MyOpenGlUtils.h"
//http://www.voidcn.com/article/p-kxegdmsr-ev.html
//顶点着色器glsl,这是define的单行定义 #x = "x"
#define GET_STR(x) #x
static const char *vertexShader = GET_STR(
        attribute vec4 a_Position;
        void main() {
            gl_Position = a_Position;
            gl_PointSize = 10.0;
        }

);

//片元着色器,软解码和部分x86硬解码
static const char *fragShader = GET_STR(
        precision mediump float ;
        uniform vec4 u_Color ;
        void main() {
            gl_FragColor = u_Color;
        }
);

char *vshader ;
char *fshader ;
GLint uColorL ;
GLint aPositionL ;
GLuint programId;
GLuint shaderFrg;
GLuint shaderVer;

void init(const char* vs2 ,const char* fs2 ){
    int vsLen = strlen(vs2);
    int fsLen = strlen(fs2);
    fsLen++;
    vsLen++;
    vshader = (char *)malloc(vsLen);
    fshader = (char *)malloc(fsLen);
    strcpy(vshader ,  vs2);
    strcpy(fshader ,  fs2);
}

void surfaceCreate(){
    float tableVerticesWithTriangles[] = {
            //trangle 1
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,

            //trangle 2
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f ,

            // line 1
            -0.5f , 0.0f ,
            0.5f , 0.0f ,

            // mallets
            0.0f , -0.25f ,
            0.0f , 0.25f
    };
    shaderVer = compileShader(GL_VERTEX_SHADER , vertexShader);
    shaderFrg = compileShader(GL_FRAGMENT_SHADER , fragShader);
    programId = createProgram(shaderVer , shaderFrg);

    uColorL = glGetUniformLocation(programId , "u_Color");
    aPositionL = glGetAttribLocation(programId , "a_Position");
    checkGlError(" glGetAttribLocation ");
    LOGE(" COLOR LOCATION %d , aPositionLocation %d " , uColorL , aPositionL);
    glVertexAttribPointer(aPositionL , 2 , GL_FLOAT , GL_FALSE , 0 , tableVerticesWithTriangles);
    /**
     * 使能定点数组
     */
    glEnableVertexAttribArray(aPositionL);
    checkGlError("glEnableVertexAttribArray");
}

void viewChange(int width , int height){
    glViewport(0.0f , 0.0f , width , height);
    checkGlError("glViewport");
}

void drawFrame(){
    glClear(GL_COLOR_BUFFER_BIT);
    glClearColor(0.0f , 0.0f ,0.0f,1.0f);
    glUseProgram(programId);
    /**
     * 使用 glUniform4f 更新着色器中的u_color的值
     */
    glUniform4f(uColorL , 1.0f , 1.0f , 1.0f , 1.0f);
    //画桌子
    glDrawArrays(GL_TRIANGLES , 0 , 6);
//    checkGlError("glDrawArrays_1");
    /**
     * 绘制分割线
     */
    glUniform4f(uColorL , 1.0f , 0.0f , 0.0f , 1.0f);
    glDrawArrays(GL_LINES , 6 , 2);
//    checkGlError("glDrawArrays_2");
    /**
     * 绘制点
     */
    glUniform4f(uColorL , 0.0f , 0.0f , 1.0f , 1.0f);
    glDrawArrays(GL_POINTS , 8 , 1);
//    checkGlError("glDrawArrays_3");
    /**
     * 绘制点
     */
    glUniform4f(uColorL , 0.0f , 0.0f , 1.0f , 1.0f);
    glDrawArrays(GL_POINTS , 9 , 1);
    checkGlError("glDrawArrays_4");

}

void checkGlError(const char *op) {
    for (GLint error = glGetError(); error; error = glGetError()) {
        LOGI("after %s() glError (0x%x)\n", op, error);
    }
}
