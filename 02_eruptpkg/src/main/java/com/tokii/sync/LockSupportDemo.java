package com.tokii.sync;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockSupport可以在线程内的任意位置让线程阻塞，和Thread.suspend相比弥补了由于resume()在前发生导致线程无法继续执行的情况。
 *      和Object.await()相比，它不需要先获得某个对象的锁也不会抛出InterruptException异常。
 */
public class LockSupportDemo {
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{
        static ReentrantLock lock = new ReentrantLock();
        public ChangeObjectThread(String name){
            super.setName(name);
        }
        @Override
        public void run() {
            lock.lock();
            System.out.println(" in "+getName());
            LockSupport.park(); //线程park挂起后，线程状态会变为WAITING，不会是suspend那样的runnable
            lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        //lockSupport采用了类似信号量机制，为每个线程准备一个许可，如果许可可用park函数会立即返回，并消费这个许可。如果许可不可用就会阻塞。
        //unpark()使得一个许可变的可用，许可不会累加，不会超过一个。
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();t2.join();
    }
}
