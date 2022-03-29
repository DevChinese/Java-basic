package github.heyxhh.concurrency.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "c.ThreadPoolUsingDemo")
public class ThreadPoolUsingDemo {
    public static void main(String[] args) {
        ExecutorService executor =  Executors.newFixedThreadPool(2, new ThreadFactory() {
            private final AtomicInteger threadNo = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "pool-thread-" + threadNo.getAndIncrement());
            }
        });

        executor.submit(() -> {
            log.debug(Thread.currentThread().getName());
        });

        executor.submit(() -> {
            log.debug(Thread.currentThread().getName());
        });

        executor.submit(() -> {
            log.debug(Thread.currentThread().getName());
        });


    }
}
