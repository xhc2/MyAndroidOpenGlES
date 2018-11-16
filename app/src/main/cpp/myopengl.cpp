//
// Created by Administrator on 2018/10/19/019.
//


#include "myopengl.h"


float vertexVertices[] = {
        1.0f, 1.0f,
        -1.0f, 1.0f,
        1.0f,  -1.0f,
        -1.0f,  -1.0f
//            -1.0f, -1.0f,
//            1.0f, -1.0f,
//            1.0f,  1.0f,
//            -1.0f,  1.0f,
};
float textureVertices[] = {
        0.0f,  1.0f,
        1.0f,  1.0f,
        0.0f,  0.0f,
        1.0f,  0.0f
//            0.0f, 1.0f,
//            1.0f, 1.0f,
//            1.0f, 0.0f,
//            0.0f, 0.0f
};

MyOpenGl::MyOpenGl(const char* vs , const char* fs, int width , int height , int yuvType){
    int vsLen = strlen(vs);
    int fsLen = strlen(fs);
    fsLen++;
    vsLen++;
    this->vs = (char *)malloc(vsLen);
    this->fs = (char *)malloc(fsLen);
    strcpy(this->vs ,  vs);
    strcpy(this->fs ,  fs);
    this->width = width;
    this->height = height;
    this->yuvType = yuvType;

    y = (unsigned char *)malloc(width * height);
    u = (unsigned char *)malloc(width * height / 4);
    v = (unsigned char *)malloc(width * height / 4);

    FILE *yuvF = fopen("sdcard/FFmpeg/oneframe.yuv"  , "rb");
    if(yuvF != NULL){
        int len = fread(y , 1 , width * height , yuvF);
        LOGE(" Y LEN %d " , len );
        len =  fread(u , 1 , width * height / 4, yuvF);
        LOGE(" U LEN %d " , len );
        len = (int)fread(v , 1 , width * height / 4, yuvF);
        LOGE(" V LEN %d " , len );
    }
    FILE *fileO = fopen("sdcard/FFmpeg/testOnframe2.yuv" , "wb+");
    fwrite(y , 1 , width * height , fileO);
    fwrite(u , 1 , width * height / 4, fileO);
    fwrite(v , 1 , width * height / 4, fileO);
    fclose(fileO);
}



void MyOpenGl::createSurface(){

    shaderVer = compileShader(GL_VERTEX_SHADER , this->vs);
    shaderFrg = compileShader(GL_FRAGMENT_SHADER , this->fs);
    program = createProgram(shaderVer , shaderFrg);
    LOGE(" program %d " , program);
    textureYL = glGetUniformLocation(program, "yTexture");
    textureUL = glGetUniformLocation(program, "uTexture");
    textureVL = glGetUniformLocation(program, "vTexture");

    aPositionL = glGetAttribLocation(program, "aPosition");
    aTextureCoordinatesL = glGetAttribLocation(program, "aTexCoord");

    GLuint *textureIds = initTextTure();
    if(textureIds == NULL){
        LOGE(" initTextTure FAILD !");
        return ;
    }
    textureYid = textureIds[0];
    textureUid = textureIds[1];
    textureVid = textureIds[2];

//    glUseProgram(program);

    glVertexAttribPointer(aPositionL, 2, GL_FLOAT , GL_FALSE , 0, vertexVertices);
    glEnableVertexAttribArray(aPositionL);

    glVertexAttribPointer(aTextureCoordinatesL, 2, GL_FLOAT, GL_FALSE, 0, textureVertices);
    glEnableVertexAttribArray(aTextureCoordinatesL);
}

void MyOpenGl::surfaceChange(int width , int height){
    LOGE(" surfaceChange width %d , heigth %d  " , width , height);
    glViewport(0.0f , 0.0f , width , height);
    checkGlError(" glActiveTexture ");
}

void MyOpenGl::drawFrame(){

    glClearColor(0.0f , 0.0f , 0.0f , 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glUseProgram(program);



    glActiveTexture(GL_TEXTURE0);
    checkGlError(" glActiveTexture ");
    glBindTexture(GL_TEXTURE_2D, textureYid);
    checkGlError(" glBindTexture ");
    glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width, height, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, y);
    checkGlError(" glTexImage2D ");
    glUniform1i(textureYL, 0);
    checkGlError(" glUniform1i ");


    glActiveTexture(GL_TEXTURE1);
    glBindTexture(GL_TEXTURE_2D, textureUid);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width / 2 , height / 2, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, u);
    glUniform1i(textureUL, 1);

    glActiveTexture(GL_TEXTURE2);
    glBindTexture(GL_TEXTURE_2D, textureVid);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width / 2, height / 2, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, v);
    glUniform1i(textureVL, 2);

    glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
//    LOGE(" drawframe   width %d , heigth %d  " , width , height);
    checkGlError(" drawframe ");
}


void MyOpenGl::showYuv(unsigned char *tempY , unsigned char *tempU , unsigned char *tempV){
    memcpy(y , tempY , width * height);
    memcpy(u , tempU , width * height / 4);
    memcpy(v , tempV , width * height / 4);

}


 void MyOpenGl::checkGlError(const char *op) {
    for (GLint error = glGetError(); error; error = glGetError()) {
        LOGI("after %s() glError (0x%x)\n", op, error);
    }
}




MyOpenGl::~MyOpenGl(){

}
