package com.tokii.erupt;

/**
 * 线程挂起释放资源实例
 */
public class GoodSuspend {
    public static Object o = new Object();
    public static class ChangeObjectThread extends Thread{
        volatile boolean suspendMe = false;
        //挂起线程
        public void suspendMe(){
            suspendMe = true;
        }
        //继续执行线程
        public void resumeMe(){
            suspendMe = false;
            synchronized (this){
                notify();
            }
        }

        @Override
        public void run() {
            while (true){
                //该线程被挂起的时候进入等待状态
                synchronized (this){
                    while (suspendMe){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // 该线程的业务逻辑
                synchronized (o){
                    System.out.println("in changeObjThread...");
                }
                Thread.yield();
            }
        }
    }
    //读取标记
    public static class ReadObjectThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (o){
                    System.out.println("in readObjThread...");
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread();
        ReadObjectThread t2 = new ReadObjectThread();
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t1.suspendMe();
        System.out.println("suspend t1 2 sec...");
        Thread.sleep(2000);
        System.out.println("resume t1...");
        t1.resumeMe();
    }
}
