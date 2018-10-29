package com.tokii.sync;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  读写锁可以有效的减少锁竞争，提升系统性能。
 *  重入锁或者内部锁对于读写都是串行的。
 *  由于读操作不会破坏数据的完整性，因此读写锁允许多个线程同时读，阻塞写。
 */
public class ReadWriteLockDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;
    // 模拟读操作
    public Object handleRead(Lock lock) throws InterruptedException {
        try{
            lock.lock();
            Thread.sleep(1000); //读操作耗时越多读写锁优势越明显
            return value;
        }finally {
            lock.unlock();
        }
    }
    public void handleWrite(Lock lock,int index) throws InterruptedException {
        try{
            lock.lock();
            Thread.sleep(1000);
            value = index;
        }finally {
            lock.unlock();
        }
    }
    //使用读写锁，在读操作较多且耗时的时候会有更好的性能
    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleRead(readLock);
//                    demo.handleRead(lock); // 如果读写操作均使用重入锁，则20个线程将执行20余秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        final Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
//                    demo.handleWrite(lock,new Random().nextInt());
                    demo.handleWrite(writeLock,new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for(int i=0;i<18;i++){
            new Thread(readRunnable).start();
        }
        for (int i=18;i<20;i++){
            new Thread(writeRunnable).start();
        }
    }
}
