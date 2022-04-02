package github.heyxhh.concurrency.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author huazai
 */
@Slf4j(topic = "c.ScheduledThreadPoolDemo")
public class ScheduledThreadPoolDemo {
    public static void main(String[] args) {
        // testScheduledThreadPool();
        // testScheduleAtFixedRate();
        testScheduleWithFixedRate();

    }

    public static void testScheduleWithFixedRate() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

        // 每一轮调度执行完，在过delay时间，循环调度
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            log.debug("running...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public static void testScheduleAtFixedRate() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

        // 每隔1s执行yici
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            log.debug("running...");
        }, 1, 1, TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            log.debug("running...");
            try {
                TimeUnit.SECONDS.sleep(2);  // 执行时间，会影响调度频率
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public static void testScheduledThreadPool() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "ss");
            }
        });

        scheduledThreadPoolExecutor.schedule(() -> {
            log.debug("1");
            int a = 1 / 0; // 异常不会抛出去，不影响其他线程运行
        }, 1, TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.schedule(() -> {
            log.debug("2");
        }, 1, TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.schedule(() -> {
            log.debug("3");
        }, 1, TimeUnit.SECONDS);
    }
}
