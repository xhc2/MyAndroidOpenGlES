//
// Created by Administrator on 2018/10/19/019.
//


#include "myopengl.h"


float vertexVertices[] = {
        1.0f, 1.0f,
        -1.0f, 1.0f,
        1.0f,  -1.0f,
        -1.0f,  -1.0f
};
float textureVertices[] = {
        0.0f,  1.0f,
        1.0f,  1.0f,
        0.0f,  0.0f,
        1.0f,  0.0f
};

MyOpenGl::MyOpenGl(const char* path , const char* vs , const char* fs, int width , int height , int yuvType){
    initShaderFlag = false;
    int vsLen = (int)strlen(vs);
    int fsLen =(int)strlen(fs);
    int pathLen = (int)strlen(path);
    fsLen++;
    vsLen++;
    pathLen++;

    this->path = (char *)malloc(pathLen);
    this->vs = (char *)malloc(vsLen);
    this->fs = (char *)malloc(fsLen);
    strcpy(this->vs ,  vs);
    strcpy(this->fs ,  fs);
    strcpy(this->path ,path);
    this->width = width;
    this->height = height;
    this->yuvType = yuvType;

    y = (unsigned char *)malloc(width * height);
    u = (unsigned char *)malloc(width * height / 4);
    v = (unsigned char *)malloc(width * height / 4);
    yuvF = fopen(this->path , "rb");
    if(yuvF == NULL){
        LOGE(" OEPN FILE FAILD ! ");
        return ;
    }


}

void MyOpenGl::run(){

    int yLen  = width * height;
    while(!isExit){
        threadSleep(0 , 40000000); //40 ms
        if(fread(y , 1, yLen , yuvF ) != yLen){
            break;
        }
        fread(u , 1, yLen / 4, yuvF );
        fread(v , 1, yLen / 4, yuvF );

    }
    LOGE(" ..... end... ");
}


void MyOpenGl::initShader(){
    LOGE(" %s  \n\n\n %s",vs ,fs);
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

    glVertexAttribPointer(aPositionL, 2, GL_FLOAT , GL_FALSE , 0, vertexVertices);
    glEnableVertexAttribArray(aPositionL);

    glVertexAttribPointer(aTextureCoordinatesL, 2, GL_FLOAT, GL_FALSE, 0, textureVertices);
    glEnableVertexAttribArray(aTextureCoordinatesL);
    initShaderFlag = true;
        this->start();
}
void MyOpenGl::createSurface(){

    LOGE(" ----------------- surface create -----------------");
    initShader();
}

void MyOpenGl::surfaceChange(int width , int height){
    LOGE(" surfaceChange width %d , heigth %d  " , width , height);
    glViewport(0.0f , 0.0f , width , height);
    checkGlError(" glActiveTexture ");
}

void MyOpenGl::myDraw(){
    glClearColor(0.0f , 0.0f , 0.0f , 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glUseProgram(program);

    glActiveTexture(GL_TEXTURE0);
    checkGlError(" glActiveTexture ");
    glBindTexture(GL_TEXTURE_2D, textureYid);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width, height, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, y);
    glUniform1i(textureYL, 0);


    glActiveTexture(GL_TEXTURE1);
    glBindTexture(GL_TEXTURE_2D, textureUid);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width / 2 , height / 2, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, u);
    glUniform1i(textureUL, 1);

    glActiveTexture(GL_TEXTURE2);
    glBindTexture(GL_TEXTURE_2D, textureVid);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, width / 2, height / 2, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, v);
    glUniform1i(textureVL, 2);

    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
}

void MyOpenGl::drawFrame(){
    if(initShaderFlag){
        myDraw();
    }

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
    this->stop();
    this->join();
    glDisableVertexAttribArray(aPositionL);
    glDisableVertexAttribArray(aTextureCoordinatesL);
    glDetachShader(program, shaderVer );
    glDetachShader(program, shaderFrg);
    glDeleteShader(shaderVer);
    glDeleteShader(shaderFrg);
    glDeleteProgram(program);
    glDeleteTextures(1, &textureYid);
    glDeleteTextures(1, &textureUid);
    glDeleteTextures(1, &textureVid);
    free(y);
    free(u);
    free(v);
    free(path);
    free(vs);
    free(fs);

}
