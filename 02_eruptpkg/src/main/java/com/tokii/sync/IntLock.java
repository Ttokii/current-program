package com.tokii.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的中断
 */
public class IntLock implements Runnable {
    public static ReentrantLock rl1 = new ReentrantLock();
    public static ReentrantLock rl2 = new ReentrantLock();
    int lock;
    //控制加锁顺序，构成死锁
    public IntLock(int lock){
        this.lock = lock;
    }
    @Override
    public void run(){
        try{
            if(lock == 1){
                //rl1.lockInterruptibly();可以对中断进行响应的锁申请请求
                rl1.lockInterruptibly();
                Thread.sleep(500);
                rl2.lockInterruptibly();
            }else{
                rl2.lockInterruptibly();
                Thread.sleep(500);
                rl1.lockInterruptibly();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            if(rl1.isHeldByCurrentThread()) rl1.unlock();
            if(rl2.isHeldByCurrentThread()) rl2.unlock();
        }
    }

    /**
     * 1、t1占用rl1,在申请占用rl2；t2占用rl2,在申请占用rl1，因此会形成t1和t2之间的相互等待。
     * 2、t2在1秒之后线程被中断，此时t2会放弃对rl1的申请，同时释放自己的rl2。这时t1可以顺利的执行下去。
     */
    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();t2.start();
        Thread.sleep(3000);
        t2.interrupt();
    }
}
