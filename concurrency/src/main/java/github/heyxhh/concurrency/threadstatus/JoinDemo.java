package github.heyxhh.concurrency.threadstatus;

import java.util.concurrent.TimeUnit;

public class JoinDemo {

    private static int r = 0;

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                r = 2;
                System.out.println("子线程运行结束。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.start();

        System.out.println("join前r的结果: " + String.valueOf(r));  // 0

        try {

            t.join(TimeUnit.SECONDS.toSeconds(1));  // 可设置join时长，超过时长等待的线程还没结束直接返回

            System.out.println("join(1s)后r的结果: " + String.valueOf(r));  // 1

            t.join();  // 阻塞等待t1执行完，要等待哪个线程，就对那个线程对象调用join方法

            System.out.println("join后r的结果: " + String.valueOf(r));  // 2
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
}
