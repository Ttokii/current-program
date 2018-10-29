package com.tokii.thread.pool;

import java.util.concurrent.*;

/**
 * 拒绝策略：多线程环境下，线程太多，如何拒绝。
 */
public class RejectThreadPoolDemo {
    public static class MyTask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+":Thread ID:"+Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * MyTask执行完成需要100毫秒，线程池每10毫秒创建一个待执行线程，这样就会形成很多线程拒绝执行。
     */
    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(
                5, // corePoolSize：指定线程池中线程数量
                5,  //maximumPoolSize：指定线程池中最大线程数量
                0L, //keepAliveTime：当线程池线程数量超过corePoolSize时，多余的空闲线程的存活时间
                TimeUnit.SECONDS, // unit：keepAliveTime的时间单位
                new LinkedBlockingDeque<Runnable>(10), //workQueue：任务队列，任务被提交但未被执行的任务，此为无界任务队列
                Executors.defaultThreadFactory(), //threadFactory：线程工厂，用于创建线程，一般用默认的即可。
                new RejectedExecutionHandler() { //handler：拒绝策略，当任务太多来不及执行，如何拒绝。该策略为丢弃掉无法处理任务
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor){
                        System.out.println(r.toString()+" is Discard...");
                    }
                });
        for (int i = 0; i < Integer.MAX_VALUE; i++){
            es.submit(task);
            Thread.sleep(10);
        }
    }
}
