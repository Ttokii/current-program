package com.tokii.sync;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程挂起后响应中断的实例
 */
public class LockSupportWithInterruptDemo {
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{
        public static ReentrantLock lock = new ReentrantLock();
        public ChangeObjectThread(String name){
            super.setName(name);
        }
        @Override
        public void run() {
            lock.lock();
            System.out.println("in "+getName());
            LockSupport.park(); // park挂起过程中出现中断将立即响应，继续往下执行
            if(Thread.interrupted()){ //判断是否被中断，并清除中断标记
                System.out.println("in "+getName()+" 被中断了...");
            }
            lock.unlock();
            System.out.println(getName()+"执行结束...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
