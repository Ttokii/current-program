package com.tokii.erupt;

/**
 * 指令重排问题: 指令重排会保证单线程内语义串行性
 */
public class OrderExample {
    int a = 0;
    boolean flag = false;
    // 指令重排后可能会变为：flag = false;a = 1;
    // 单线程执行语义灭有变化,但多线程可能会发生问题，例如：线程A执行writer()，线程B执行reader()
    public void writer(){
        a = 1;
        flag = false;
    }
    public void reader(){
        if(flag){
            int i = a + 1;
            // ...
        }
    }

    public enum State{
        NEW, //新创建的线程，没有开始执行，没有调用start()方法
        RUNNABLE, //线程正在运行中
        BLOCKED, //线程遇到了synchronized同步,处于暂停状态，直到获得请求的锁
        WAITING, //表示一个无时间限制的等待，等待一个特殊事件
        TIMED_WAITING, //表示一个有时间限制的等待，等待一个特殊事件
        TERMINATED //执行完毕已经结束
    }
}
