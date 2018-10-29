package com.tokii.erupt;

/**
 * 被volatile修饰的变量，可以让程序范围内的所有线程都能看到这变量的修改。
 * 因为编译器的优化规则如果不使用声明变量volatile，其它线程可能不会被通知到甚至别的线程中看到的修改顺序都会是反的。
 * 一旦使用volatile虚拟机会特殊处理这种情况。
 * volatile对于保证操作的原子性有非常大的帮助，但是volatile不能代替锁，无法保证一些符合操作的原子性。
 */
public class VolatileCase {
    /**
     * 复合操作的累加不是原子性的
     * 如果是原子性则最后结果应该是100000,10个线程每个线程累加10000次，但输出结果总小于100000
     */
    static volatile int k = 0;
    public static class PlusTask implements Runnable{
        @Override
        public void run(){
            for (int i=0;i<10000;i++){
                k++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for(int i=0;i<10;i++){
            ts[i] = new Thread(new PlusTask());
            ts[i].start();
        }
        for(int i=0;i<10;i++){
            ts[i].join();
        }
        System.out.println(k);
    }
}
