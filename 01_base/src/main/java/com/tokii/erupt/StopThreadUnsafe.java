package com.tokii.erupt;

/**
 * 线程的stop()方法引发的数据不一致问题
 */
public class StopThreadUnsafe {

    public static User user = new User();
    public static class User{
        private int id;
        private String name;
        public User(){
            id = 0;
            name = "0";
        }
        public int getId() {return id;}
        public void setId(int id) {this.id = id;}
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    //为user对象赋值, 该类的注释部分为改进的stop方法，可以保证程序执行完后stop
    public static class ChangeObjThread extends Thread{
//        volatile boolean stopMe = false;
//        public void stopMe(){
//            stopMe = true;
//        }
        @Override
        public void run(){
            while (true){
//                if(stopMe){
//                    System.out.println("programmer exit...");
//                    break;
//                }
                synchronized (user){
                    int v = (int) (System.currentTimeMillis() / 1000);
                    user.setId(v);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName(String.valueOf(v));
                }
                Thread.yield();
            }
        }
    }
    //打印不一致的数据
    public static class ReadObjThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (user){
                    if(!String.valueOf(user.getId()).equals(user.getName())){
                        System.out.println(user.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjThread().start();
        while (true){
            Thread t = new ChangeObjThread();
            t.start();
            Thread.sleep(150);
            t.stop();
        }
    }
}
