package com.tokii.erupt;

/**
 * 线程的结束和谦让: 一个线程的输入可能依赖于其它线程的输出，此时该线程需要等待依赖线程执行结束才能继续执行。
 *                 可以用join()完成这个操作。join()的本质是让调用线程等待wait()在当前线程对象实例上。
 *                 当前线程执行完前会调用notifyAll()通知所有等待的线程继续执行，因此不要在被等待的线程中使用wait()或者notify()等方法。
 */
public class JoinMain {
    public volatile static int i = 0;
    public static class AddThread extends Thread{
        @Override
        public void run() {
            for(i=0;i<10000000;i++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread t = new AddThread();
        t.start();
        t.join();
        System.out.println(i);
    }
}
