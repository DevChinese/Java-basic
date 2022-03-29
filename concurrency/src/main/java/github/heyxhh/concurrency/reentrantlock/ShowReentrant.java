package github.heyxhh.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 同一线程可对 ReentrantLock 多次加锁，即 ReentrantLock 是可重入的
 */
@Slf4j(topic = "c.ShowReentrant")
public class ShowReentrant {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        method1();
        
    }

    private static void method1() {
        lock.lock();
        try {
            log.debug("method1 获得锁");
            method2();
        } finally {
            log.debug("method1 释放锁");
        }
    }

    private static void method2() {
        lock.lock();
        try {
            log.debug("method2 获得锁");
            method3();
        } finally {
            log.debug("method2 释放锁");
        }
    }

    private static void method3() {
        lock.lock();
        try {
            log.debug("method3 获得锁");
        } finally {
            log.debug("method3 释放锁");
        }
    }


    
}
