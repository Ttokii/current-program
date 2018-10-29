package com.tokii.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁可以完全替代synchronized关键字。JDK6及其之后两者性能相差不大，JDK6之前重入锁性能优于synchronized
 * 用法和synchronized相同，如果声明为静态变量相当于类锁，普通变量相当于对象锁
 * 注意：重入锁锁定几次就要释放几次，释放多的话会抛出java.lang.IllegalMonitorStateException异常，释放少的话会其它进程将无法进入临界区
 */
public class ReenterLock implements Runnable {
    ReentrantLock lock = new ReentrantLock();
    public static int i = 0;
    @Override
    public void run(){
        for (int j=0;j<10000000;j++){
            lock.lock();
            try{
                i++;
            }finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ReenterLock rl = new ReenterLock();
        Thread t1 = new Thread(rl);
        Thread t2 = new Thread(rl);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
