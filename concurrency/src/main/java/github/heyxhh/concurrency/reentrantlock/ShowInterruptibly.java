package github.heyxhh.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 调用 ReentrantLock 对象的 lockInterruptibly 获取锁，若获取不到锁时，可被其他线程调用 interrupt 打断
 * 注意: ReentrantLock 对象在调用 lock 或者 lockInterruptibly 方法获取不到锁时，会进入到 WAITING 状态，而不是 BLOCKED 状态
 */
@Slf4j(topic = "c.ShowInterruptibly")
public class ShowInterruptibly {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
         testLockInterruptibly();

        //  testLock();
    }

    public static void testLockInterruptibly() {
        Thread t1 = new Thread(() -> {
            log.debug("子线程: 尝试获得锁。。。");
            try {
                // 如果没竞争或者竞争成功，此方法会成功获得锁
                // 如果竞争失败就进入阻塞队列，但是可以被其他线程用 interrupt 方法打断; 而lock方法是不可以被打断的
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("子线程: 被打断了。。。");
                log.debug("子线程: 子线程被打断唤醒后的打断标志: {}", Thread.currentThread().isInterrupted()); // false; lock被唤醒后会清除打断标志
                e.printStackTrace();
                return;
            }

            try {
                log.debug("子线程: 获得了锁。。。");
            } finally {
                lock.unlock();
            }

        });

        lock.lock(); // 主线程先取锁，让子线程获取不到锁

        t1.start();

        try {
            Thread.sleep(1000); // 让子线程先运行的阻塞状态
            log.debug("main: 子线程被打断前的线程状态: {}", t1.getState()); //  WAITING, 注意不是BLOCKED状态
            t1.interrupt(); // 打断t1线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 使用lock方法，子线程不会被打断，因此最终子线程获得了锁
     */
    public static void testLock() {
        Thread t1 = new Thread(() -> {
            log.debug("子线程: 尝试获得锁。。。");
            
            lock.lock();
            
            try {
                log.debug("子线程: 获得了锁。。。");
                log.debug("子线程: 线程被打断唤醒后的打断标志: {}", Thread.currentThread().isInterrupted());  // true，不会被打断，也不会清除打断标志
            } finally {
                lock.unlock();
            }

        });

        lock.lock(); // 主线程先取锁，让子线程获取不到锁

        t1.start();

        try {
            Thread.sleep(1000);
            log.debug("main: 子线程被打断前的线程状态: {}", t1.getState()); //  WAITING
            t1.interrupt(); // 打断t1线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
