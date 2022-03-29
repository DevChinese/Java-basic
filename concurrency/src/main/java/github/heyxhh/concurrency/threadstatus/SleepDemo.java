package github.heyxhh.concurrency.threadstatus;

import java.util.concurrent.TimeUnit;

/**
 * 1. 调用 sleep 会让当前线程从 Running 进入 Timed Waiting 状态(阻塞)
 * 2. 其它线程可以使用 interrupt 方法打断正在睡眠的线程，这时 sleep 方法会抛出 InterruptedException 
 * 3. 睡眠结束后的线程未必会立刻得到执行
 * 4. 建议用 TimeUnit 的 sleep 代替 Thread 的 sleep 来获得更好的可读性
 * 5. 注意：sleep不会释放对象锁
 */

public class SleepDemo {

    public static void main(String[] args) {
        
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000L);
                System.out.println("Child thread is running...");
            } catch (InterruptedException e) {  // 有可能被打断睡眠
                e.printStackTrace();
            }
        });

        t.start();

        try {
            Thread.sleep(200L);

            System.out.println("子线程被interrupt前的状态: " + t.getState());  // TIMED_WAITING

            t.interrupt();

            TimeUnit.MILLISECONDS.sleep(200);  // 推荐使用TimeUnit代替Thread.sleep
            
            // 输出TERMINATED，说明子线程被interrupt后被唤醒（因为捕获住了异常，程序没截止）
            System.out.println("子线程被interrupt后的状态: " + t.getState());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
    }
    
}
