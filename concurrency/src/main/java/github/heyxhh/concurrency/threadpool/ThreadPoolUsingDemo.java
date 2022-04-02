package github.heyxhh.concurrency.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "c.ThreadPoolUsingDemo")
public class ThreadPoolUsingDemo {
    public static void main(String[] args) throws InterruptedException {
         testFixedThreadPoolDemo();
        // testSynchronousQueue();
        // testSingleThreadExecutor();


    }


    public static void testSingleThreadExecutor() {
        ExecutorService executor =  Executors.newSingleThreadExecutor();

        // 该线程失败，不会影响其他线程执行
        executor.execute(() -> {
            int i = 1 / 0;
            log.debug("1");
        });

        executor.execute(() -> {
            log.debug("2");
        });

        executor.execute(() -> {
            log.debug("3");
        });
    }


    /**
     * SynchronousQueue 是 CachedThreadPool 的等待队列，其特征是无容量，每次插入数据时若无线程来取走，就会被阻塞
     * @throws InterruptedException
     */
    public static void testSynchronousQueue() throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        queue.put(1); // 若 1 没被取走，后续无法往里面存放数据

        System.out.println("数据1被取走");  // 没执行到

        queue.put(2);


    }

    /**
     * FixedThreadPool 的特征是线程数是固定的，所有线程都是core线程，线程等待队列是无限大的
     */
    public static void testFixedThreadPoolDemo() {
        ExecutorService executor =  Executors.newFixedThreadPool(2, new ThreadFactory() {
            private final AtomicInteger threadNo = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "pool-thread-" + threadNo.getAndIncrement());
            }
        });

        executor.execute(() -> {
            log.debug(Thread.currentThread().getName());
        });

        executor.execute(() -> {
            log.debug(Thread.currentThread().getName());
        });

        executor.execute(() -> {
            log.debug(Thread.currentThread().getName());
        });
    }
}
