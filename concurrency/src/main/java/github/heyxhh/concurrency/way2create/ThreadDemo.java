package github.heyxhh.concurrency.way2create;

/**
 * 创建方式1：继承Thrad类，并重写run方法
 */
class ThreadWay extends Thread{

    @Override
    public void run() {
        System.out.println("child thread is running...");
    }

}

public class ThreadDemo {
    public static void main(String[] args) {
        ThreadWay t1 = new ThreadWay();
        t1.start();  // 必须要启动，线程才会从new状态变为runnable状态；start不能多次调用
    }
}
