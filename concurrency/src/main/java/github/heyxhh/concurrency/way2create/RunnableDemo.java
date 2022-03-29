package github.heyxhh.concurrency.way2create;

/**
 * 创建线程方式二：通过Runnable接口
 */
public class RunnableDemo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> System.out.println("Thread is running..."));
        t.start();
        
    }
    
}
