package com.tokii.sync;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier多线程并发控制工具，和CountDownLatch相似，但功能比CountDownLatch强大。
 *      这个线程计数器可以反复使用，首先将计数器设置为10，当凑齐第一批10个线程后计数器就会归零，然后凑齐下一批10个线程。
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable{
        private String soldier;
        private CyclicBarrier cyclic;
        Soldier(CyclicBarrier cyclic,String soldier){
            this.cyclic = cyclic;
            this.soldier = soldier;
        }
        @Override
        public void run() {
            try {
                // 当等待的线程数到达10个时，调用BarrierRun的run方法。
                // 当再调用一次CyclicBarrier.await()时会进入下一轮计数。
                cyclic.await();
                doWork();
                cyclic.await();
            } catch (InterruptedException e) { //计数器等待过程中线程被中断将会抛出此异常
                e.printStackTrace();
            } catch (BrokenBarrierException e) { //说明CyclicBarrier已经被损坏，没有办法等到所有线程到齐，可以避免永久的等待。抛出此异常。
                e.printStackTrace();
            }
        }

        void doWork(){
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier+"任务完成...");
        }
    }
    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;
        public BarrierRun(boolean flag,int N){
            this.flag = flag;
            this.N = N;
        }
        @Override
        public void run() {
            if(flag){
                System.out.println("司令：【士兵"+N+"个，任务完成！】");
            }else{
                System.out.println("司令：【士兵"+N+"个，集合完毕！】");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
            Thread[] allSolider = new Thread[N];
            boolean flag = false;
            //当cyclic等待的线程数达到N时，将会触发一次BarrierRun线程。
            CyclicBarrier cyclic = new CyclicBarrier(N,new BarrierRun(flag,N));
            //设置屏障点，主要是为了执行这个方法。
            System.out.println("集合队伍！");
            for(int i=0;i<N;i++){
                System.out.println("士兵 "+ i +" 报道!");
                allSolider[i] =  new Thread(new Soldier(cyclic,"士兵"+i));
                allSolider[i].start();
        }
    }
}
