package github.heyxhh.concurrency.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ShowTryLock")
public class ShowTryLock {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        // testTryLockWithoutTimeout();
        testTryLockWithTimeout();
    }

    public static void testTryLockWithoutTimeout() {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁。。。");

            if(!lock.tryLock()) {  // 若没获得锁，立即返回false
                log.debug("没获得锁。。。");
                return;
            }

            try {
                log.debug("获得了锁。。。");
            } finally {
                lock.unlock();
            }

        }, "t1");

        log.debug("尝试获得锁。。。");
        lock.lock();

        t1.start();

        try {
            log.debug("主线程获得了锁。。。");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        
    }

    public static void testTryLockWithTimeout() {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁。。。");

            try {
                if(!lock.tryLock(3, TimeUnit.SECONDS)) {  // 若没获得锁，立即返回false
                    log.debug("没获得锁。。。");
                    return;
                }
            } catch (InterruptedException e) {
                log.debug("没获得锁，被打断。。。");
                e.printStackTrace();
                return;  // 这里若不return, 后续unlock会有问题
            }

            try {
                log.debug("获得了锁。。。");
            } finally {
                lock.unlock();
            }

        }, "t1");

        log.debug("尝试获得锁。。。");
        lock.lock();

        t1.start();

        try {
            log.debug("主线程获得了锁。。。");
            Thread.sleep(1000);
            t1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        
    }

    
}
