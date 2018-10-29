package com.tokii.erupt;

/**
 * 在一个系统中，如果线程数量比较多，而且功能分配比较明确，就可以将相同功能的线程放置在一个线程组里。
 */
public class ThreadGroupName implements Runnable{
    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName() + "_"
                + Thread.currentThread().getName();
        while (true){
            System.out.println("I am "+groupAndName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroup tg = new ThreadGroup("testThreadGroup");
        Thread t1 = new Thread(tg,new ThreadGroupName(),"T1");
        Thread t2 = new Thread(tg,new ThreadGroupName(),"T2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount()); //打印活动线程总数
        tg.list(); //打印线程组信息，方便调试
    }
}
