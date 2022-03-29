package github.heyxhh.concurrency.sync.synchronizedusing;

public class SynchronizedFunctionDemo {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5000; ++i) {
                SynchronizedFunctionDemo2.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5000; ++i) {
                SynchronizedFunctionDemo2.decrement();
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

        System.out.println("计算后cnt的值=" + SynchronizedFunctionDemo2.getCnt());
    }
    
}


/**
 * synchronized 加在实例方法上，等价于 synchronized(this) {}
 */
class SynchronizedFunctionDemo1 {
    private int cnt = 0;

    public synchronized void increment() {
        ++cnt;
    }

    public synchronized void decrement() {
            --cnt;
    }

    public synchronized int getCnt() {
            return cnt;
    }
}


/**
 * synchronized 加在静态方法上，等价于 synchronized(this.getClass()) {}
 */
class SynchronizedFunctionDemo2 {
    private static int cnt = 0;

    public synchronized static void increment() {
        ++cnt;
    }

    public synchronized static void decrement() {
        --cnt;
    }

    public synchronized static int getCnt() {
        return cnt;
    }
}
