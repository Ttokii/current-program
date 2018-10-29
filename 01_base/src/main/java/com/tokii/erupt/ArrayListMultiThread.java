package com.tokii.erupt;

import java.util.ArrayList;

/**
 * ArrayList在并发情况下会发生错误：
 *      1、程序抛出数组越界异常：因为ArrayList在扩容过程中内部一致性被破坏造成的。
 *      2、ArrayList的size可能不会等于2000000：这是由于多线程访问冲突，使保持容器大小的变量被多线程不正常访问，同时两个线程对ArrayList中同一位置进行复制导致的。
 *
 * 并发情况下可以使用线程安全的Vector代替ArrayList解决这种问题
 */
public class ArrayListMultiThread {
    static ArrayList<Integer> al = new ArrayList<Integer>(10);
    public static class AddThread implements Runnable{
        @Override
        public void run() {
            for (int i=0;i<1000000;i++){
                al.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(al.size());
    }
}
