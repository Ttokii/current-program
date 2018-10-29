package com.tokii.erupt;

/**
 * 中断程序：线程中断不会使线程立即退出，而是想目标进行发送一个通知告知目标线程有人希望你退出，至于中断后如何处理由目标线程决定。
 */
public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(){
            @Override
            public void run(){
                while(true){
                    // 如果没有下边注释的中断判断处理逻辑，该中断没有作用
//                    if(Thread.currentThread().isInterrupted()){
//                        System.out.println("Interrupted!");
//                        break;
//                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("interrupt when sleep...");
                        //正在sleep的进程被中断会抛出InterruptedException异常，此时会清除线程的中断标记，如果还有程序需要后续处理则需要重新设置中断标记
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }
}
