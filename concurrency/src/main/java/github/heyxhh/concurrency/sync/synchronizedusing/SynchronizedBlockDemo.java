package github.heyxhh.concurrency.sync.synchronizedusing;

import java.util.concurrent.TimeUnit;

/**
 * synchronized代码块用法:
 *     Synchronized(对象) {
 *     
 *     }
 * 注意: 不同线程共享同一临界区时, 必须将 synchronized 用作同一个对象
 */

public class SynchronizedBlockDemo {

    public static void main(String[] args) {

        SynchronizedBlockDemo1 counter = new SynchronizedBlockDemo1();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5000; ++i) {
                counter.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5000; ++i) {
                counter.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("计算后cnt的值=" + counter.getCnt());


        // 验证2，看锁实例对象和锁class对象是否会竞争
        // 运行结果显示：是不会发生竞争的
        SynchronizedBlockDemo3 demo3 = new SynchronizedBlockDemo3();

        Thread t3 = new Thread(() -> {
            demo3.lockObj();
        }, "t3");

        Thread t4 = new Thread(() -> {
            demo3.lockCls();
        }, "t4");

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
    }
}

/**
 * synchronized 作用于对象上，不同的CounterDemo1对象调用increment()不会发生竞争，因为是对不同的对象加锁
 */
class SynchronizedBlockDemo1 {
    private int cnt = 0;

    public void increment() {
        synchronized(this) {  // 如果synchronized(obj)锁住的是其他对象，建议锁一个声明为final的obj
            ++cnt;
        }
    }

    public void decrement() {
        synchronized(this) {
            --cnt;
        }
    }

    public int getCnt() {
        synchronized(this) {
            return cnt;
        }
    }
}

/**
 * synchronized 作用于class上，所有CounterDemo1对象调用increment()都会发生竞争，因为他们对同一个class加锁
 * 锁类对象不会与锁实例对象互斥
 */
class SynchronizedBlockDemo2 {
    private static int cnt = 0;

    public void increment() {
        synchronized(this.getClass()) {
            ++cnt;
        }
    }

    public void decrement() {
        synchronized(this.getClass()) {
            --cnt;
        }
    }

    public int getCnt() {
        synchronized(this) {
            return cnt;
        }
    }
}

/**
 * 一个锁实例对象，一个锁类对象
 */
class SynchronizedBlockDemo3 {

    public void lockObj() {
        synchronized(this) {
            System.out.println("获得实例对象的锁。。。");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void lockCls() {
        synchronized(this.getClass()) {
            System.out.println("获得class对象的锁。。。");
        }
    }
}