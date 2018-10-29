package com.tokii.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量：限制线程的访问数量
 */
public class SemapDemo implements Runnable {
    final Semaphore semp = new Semaphore(5);//5代表能同时申请多少个许可
//    final Semaphore semp = new Semaphore(5,true);//5代表能同时申请多少个许可,true代表是否公平分配许可
    @Override
    public void run() {
        try {
            semp.acquire(); //只允许5个线程获得许可，超过五个的线程将等待
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + ": done !");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //五个一组输出日志
    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final SemapDemo demo = new SemapDemo();
        for (int i=0;i<20;i++){
            exec.submit(demo);
        }
    }
}
