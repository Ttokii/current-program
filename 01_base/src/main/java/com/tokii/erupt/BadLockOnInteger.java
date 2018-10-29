package com.tokii.erupt;

/**
 * 错误的加锁，Integer属于不变对象，一旦被创建就不可被修改。所以下例的程序中锁是不断变化的，导致程序发生问题
 */
public class BadLockOnInteger implements Runnable {
    public static Integer i = 0;
    static BadLockOnInteger instance = new BadLockOnInteger();
    @Override
    public void run() {
        for (int j=0;j<10000000;j++){
            synchronized (i){
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new BadLockOnInteger());
        Thread t2 = new Thread(new BadLockOnInteger());
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
