package com.tokii.erupt;

/**
 * volatile也能保证操作的可见性和有序性。
 */
public class NoVisibility {
    private static boolean ready = false;
    private static int num;

    private static class ReadThread extends Thread{
        @Override
        public void run() {
            while (!ready);
            System.out.println(num);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadThread().start();
        Thread.sleep(1000);
        num = 42;
        ready = true; //ReadThread线程并不会知道ready修改为true，所以ReadThread线程无法退出。可以使用volatile声明一个变量的修改对其它线程可见
        Thread.sleep(10000);
    }
}
