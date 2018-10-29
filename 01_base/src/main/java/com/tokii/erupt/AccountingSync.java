package com.tokii.erupt;

/**
 * 多线程下的读写会造成数据一致性问题，一次需要进行同步锁。
 * synchronized用法：
 *      指定锁对象：对给定对象加锁，进入同步代码块前需要获得给定对象的锁。
 *      直接作用于实例方法：相当于对当前实例加锁，进入当前同步代码块前需要获取当前实例锁。
 *      直接作用于静态方法：相当于对当前类加锁，进入当前同步代码块前需要获取当前的类锁。


 */
public class AccountingSync implements Runnable{
    static AccountingSync instance = new AccountingSync();
    static int i=0;
    public synchronized void add(){
        i++;
    }
    @Override
    public void run() {
        for (int j=0;j<1000000;j++){
            add();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //这里必须用同一个Runnable实例，因为对象方法加的是该对象的实例锁
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
