package com.tokii.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 扩展线程池
 */
public class ExtThreadPool {
    public static class MyTask implements Runnable{
        public String name;
        public MyTask(String name){
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println("正在执行"+":Thread ID:"+Thread.currentThread().getId()+",Task name:"+name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 可以重写ThreadPoolExecutor的beforeExecute、afterExecute、terminated方法进行扩展线程池
     * 每个线程执行前后分别调用beforeExecute、afterExecute方法，所有线程执行完毕后调用terminated方法
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(
                5,
                5,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行:"+((MyTask)r).name);
            }
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成:"+((MyTask)r).name);
            }
            @Override
            protected void terminated() {
                System.out.println("线程池退出...");
            }
        };
        for (int i = 0; i < 5; i++) {
            MyTask task = new MyTask("Task-Geym-"+i);
            es.execute(task);
            Thread.sleep(10);
        }
        es.shutdown();
    }
}
