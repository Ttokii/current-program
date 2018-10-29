package com.tokii.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁申请等待限时
 */
public class TimeLock implements Runnable {
    public static ReentrantLock rl = new ReentrantLock();
    @Override
    public void run() {
        try {
            // tryLock()接受两个参数，一个表示等待时长，另一个表示计时单位，也可以不带参数直接运行，表示等不到锁立即返回false
            // tryLock(5, TimeUnit.SECONDS)表示线程在申请锁的过程中最多等待5秒，
            //      如果超过五秒还没有得到锁就会返回false，若果得到锁就会返回true
            if(rl.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);
            }else{
                System.out.println("get lock failed ...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(rl.isHeldByCurrentThread()) rl.unlock();
        }
    }
    //t1、t2中先得到锁的线程休眠5秒，另一个线程在五秒之内不能获得锁将会返回false
    public static void main(String[] args) {
        TimeLock tl = new TimeLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(t1);
        t1.start();
        t2.start();
    }
}
