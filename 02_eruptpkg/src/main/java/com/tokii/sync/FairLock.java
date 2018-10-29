package com.tokii.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁：new ReentrantLock(true);
 */
public class FairLock implements Runnable {
    public static ReentrantLock rl = new ReentrantLock(true);
    @Override
    public void run() {
        while(true){
            try{
                rl.lock();
                System.out.println(Thread.currentThread().getName() + " get lock ");
            }finally {
                rl.unlock();
            }
        }
    }
    //可以看到结果t2和t2交替打印，说明锁是公平的
    public static void main(String[] args) {
        FairLock fl = new FairLock();
        Thread t1 = new Thread(fl,"thread_t1");
        Thread t2 = new Thread(fl,"thread_t2");
        t1.start();t2.start();
    }
}
