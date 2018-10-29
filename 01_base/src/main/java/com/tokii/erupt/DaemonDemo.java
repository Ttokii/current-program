package com.tokii.erupt;

import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * 守护线程是在后台默默完成一些系统性服务，比如垃圾回收，JIT线程。与守护线程相对的是用户线程，当一个java应用内只有守护线程时，java虚拟机会自动退出。
 */
public class DaemonDemo {
    public static class DaemonT extends  Thread{
        @Override
        public void run() {
            while (true){
                System.out.println("I am live...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new DaemonT();
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);
    }
}
