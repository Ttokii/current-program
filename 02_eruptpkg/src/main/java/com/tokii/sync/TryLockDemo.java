package com.tokii.sync;

import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo implements Runnable{
    public static ReentrantLock rl1 = new ReentrantLock();
    public static ReentrantLock rl2 =  new ReentrantLock();
    int lock;
    public TryLockDemo(int lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        if(lock == 1){
            while(true){
                if(rl1.tryLock()){
                    try{
                        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                        if(rl2.tryLock()){ //如果获取不到锁立即返回false
                            try{
                                System.out.println(Thread.currentThread().getId() + " My Job done ");
                            }finally {
                                rl2.unlock();
                            }
                        }
                    }finally {
                        rl1.unlock();
                    }
                }
            }
        }else{
            while(true){
                if(rl2.tryLock()){
                    try {
                        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                        if(rl1.tryLock()){ //如果获取不到锁立即返回false
                            try {
                                System.out.println(Thread.currentThread().getId() + " My Job done ");
                            }finally {
                                rl1.unlock();
                            }
                        }
                    }finally {
                        rl2.unlock();
                    }
                }
            }
        }
    }

    /**
     * 该实例采用了非常容易构成死锁的加锁程序，由于tryLock()不会阻塞线程所以不会构成程序的死锁。
     */
    public static void main(String[] args) {
        TryLockDemo tld1 = new TryLockDemo(1);
        TryLockDemo tld2 = new TryLockDemo(2);
        Thread t1 = new Thread(tld1);
        Thread t2 = new Thread(tld2);
        t1.start();t2.start();
    }
}
