//
// Created by Administrator on 2018/11/17/017.
//

#ifndef MYANDROIDOPENGLES_MYTHREAD_H
#define MYANDROIDOPENGLES_MYTHREAD_H

#include <pthread.h>


class MyThread
{
private:
    pthread_t pid;

private:
    static void * start_thread(void *arg);                                                       //静态成员函数
public:
    bool pause ;
    bool isExit ;
    int start();
    void stop();
    void setPause();
    void setPlay();
    void join();
    MyThread();
    ~MyThread();
    void  threadSleep(int s , long nanotime);
    pthread_mutex_t mutex_pthread ;
    virtual void run() = 0;//基类中的虚函数要么实现，要么是纯虚函数（绝对不允许声明不实现，也不纯虚）
};

#endif //MYANDROIDOPENGLES_MYTHREAD_H
