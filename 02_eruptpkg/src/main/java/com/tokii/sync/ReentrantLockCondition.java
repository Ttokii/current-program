package com.tokii.sync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的等待和唤醒
 */
public class ReentrantLockCondition implements Runnable {
    public static ReentrantLock rl = new ReentrantLock();
    public static Condition condition = rl.newCondition();
    @Override
    public void run() {
        try {
            rl.lock();
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rl.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCondition rlc = new ReentrantLockCondition();
        Thread t1 = new Thread(rlc);
        t1.start();
        Thread.sleep(2000);
        //condition.signal()方法调用时也要求获得相关的锁，否则会报异常
        // 在signal调用完成后，需要释放相关的锁，谦让给被唤醒的进程，让它可以继续执行
        rl.lock();
        condition.signal();
        Thread.sleep(5000);
        rl.unlock();
    }
}
