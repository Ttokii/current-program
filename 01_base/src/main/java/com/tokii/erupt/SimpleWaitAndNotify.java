package com.tokii.erupt;

/**
 * 线程的等待和通知：当在一个对象实例上调用wait()后，当前线程就会在这个线程上等待，直到其它线程调用了该对象的notify()后继续执行。
 *                当一个调用了object.wait()后，这个线程就会加入到这个object的等待队列里，当object.notify()被调用就会从这个队列里完全随即唤醒一个进程。
 *                notifyAll()会唤醒等待队列中的所有线程。
 *                object.wait()只能在该对象的synchronized语句中才能被调用，也就是必须先获得object的监视器。
 *                object.wait()执行后会释放当前线程获取的object锁
 */
public class SimpleWaitAndNotify {
    final static Object object = new Object();
    public static class T1 extends Thread{
        @Override
        public void run(){
            synchronized (object){
                System.out.println(System.currentTimeMillis()+": T1 start...");
                try {
                    System.out.println(System.currentTimeMillis()+": T1 wait for object...");
                    object.wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+": T1 end..."); //当线程T2执行notify并释放object锁后才会打印该语句
            }
        }
    }
    public static class T2 extends Thread{
        @Override
        public void run(){
            synchronized (object){
                System.out.println(System.currentTimeMillis()+": T2 start notify one thread...");
                object.notify();
                System.out.println(System.currentTimeMillis()+": T2 end...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }

}
