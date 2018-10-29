package com.tokii.erupt;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap并发下会发生的问题：
 *          程序正常结束，结果不符合预期，数据被破坏
 *          程序永远无法停止：hashMap内部的链表被破坏，造成死循环，CPU占用会极高
 *
 * 可以使用CurrentHashMap代替HashMap避免发生这种问题。
 */
public class HashMapMultiThread {
    static Map<String,String> map = new HashMap<String, String>();
    public static class AddThread implements Runnable{
        int start = 0;
        public AddThread(int start){
            this.start = start;
        }
        @Override
        public void run(){
            for (int i=start;i<100000;i+=2){
                map.put(Integer.toString(i),Integer.toBinaryString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread(0));
        Thread t2 = new Thread(new AddThread(1));
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(map.size());
    }
}
