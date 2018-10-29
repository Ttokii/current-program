package com.tokii.erupt;

/**
 * 原子性问题：32位jvm中long值的写操作不是原子性的，可能会发生写串位问题
 */
public class Atomicity {
    public static long t = 0;
    public static class ChangeT implements Runnable{
        public long to ;
        public ChangeT(long to){
            this.to = to;
        }
        @Override
        public void run() {
            while (true){
                Atomicity.t = to;
                Thread.yield(); //使当前线程回到可执行状态
            }
        }
    }
    public static class ReadT implements Runnable{
        @Override
        public void run() {
            while(true){
                long tmp = Atomicity.t;
                if(tmp != 111L && tmp != -999L && tmp != 333L && tmp != -444L){
                    System.out.println(tmp);
                    Thread.yield();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ChangeT(111L)).start();
        new Thread(new ChangeT(-999L)).start();
        new Thread(new ChangeT(333L)).start();
        new Thread(new ChangeT(-444L)).start();
        new Thread(new ReadT()).start();
    }
}
