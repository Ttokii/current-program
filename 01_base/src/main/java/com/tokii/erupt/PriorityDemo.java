package com.tokii.erupt;

/**
 * 线程优先级Demo
 */
public class PriorityDemo {
    public static class HightPriority extends Thread{
        static int count = 0;
        @Override
        public void run() {
            while (true){
                synchronized (PriorityDemo.class){
                    count++;
                    if(count > 10000000){
                        System.out.println("HightPriority is complete...");
                        break;
                    }
                }
            }
        }
    }
    public static class LowPriority extends Thread{
        static int count = 0;
        @Override
        public void run() {
            while (true){
                synchronized (PriorityDemo.class){
                    count++;
                    if(count > 10000000){
                        System.out.println("LowPriority is complete...");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread hight = new HightPriority();
        Thread low = new LowPriority();
        hight.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        hight.start(); //一般情况下优先级高的线程会优先完成任务
    }
}
