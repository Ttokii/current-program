package com.tokii.erupt;

/**
 * 线程的挂起和继续执行：resume继续执行一个被挂起（suspend）的线程。
 *                      但是如果resume()操作意外的在suspend()方法之前执行，被挂起的线程很难在继续执行，
 *                      挂起的线程又不会释放资源，导致系统工作不正常，而且对于挂起的线程它的运行状态是Runnable.
 */
public class BadSuspend {
    public static Object o = new Object();
    public static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    public static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super(name);
        }
        @Override
        public void run() {
            synchronized (o){
                System.out.println("in "+getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.resume(); //线程1释放资源后结束
        t2.resume(); //将线程2继续执行，这时还没有挂起
        t1.join();
        t2.join();
    }
}
