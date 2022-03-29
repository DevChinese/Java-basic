package github.heyxhh.concurrency.threadstatus;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;


// 注意：由BIO导致的线程阻塞在java里无法被区分，仍然被认为是runable状态
// question: 线程被对象锁阻塞时，能否被interrupt打断; 答案：不会
public class InterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        // interruptWaitingThread();
        // interruptRunningThread();
        interruptPack();

        // SynInterruptTest st = new SynInterruptTest();
        // st.test();

    }

    /**
     * 打断 sleep，wait，join 的线程:
     *     这几个方法都会让线程进入阻塞状态
     *     打断 sleep 的线程, 会清空打断状态，以 sleep 为例
     */
    static void interruptWaitingThread() {
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("sleeping...");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();

        t1.interrupt();

        try {
            t1.join();
            System.out.println("t1的打断标记: " + t1.isInterrupted());  // false; sleep被打断后，会把线程的打断标记清空
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打断运行中的线程，不会清楚打断标志
     */
    static void interruptRunningThread() {
        Thread t2 = new Thread(() -> {
            while (true) {
                boolean b = Thread.currentThread().isInterrupted();
                if (b) {  // 正在运行中的线程被打断不会停止运行，而是由程序自己判断要不要处理
                    System.out.println("子线程被打断了。。。");
                    break;
                }
            }
            // 可简写成一下方式
            // while(!Thread.currentThread().isInterrupted() && work to do)

            System.out.println("子线程结束死循环。。。");

            try {
                // 睡眠是为了让main线程看到打断标志的效果，因为结束的线程isInterrupted()会返回false
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                System.out.println("异常会被抛出, 因为线程的打断标志位true, sleep时会抛出异常!");
                e.printStackTrace();
            }
        }, "t2");

        t2.start();

        t2.interrupt();

        System.out.println("t2被打断后的打断标志状态: " + t2.isInterrupted());
        // 还可以使用interrupted()方法判断打断标志，但是调用t2.interrupted()方法在判断完后会清除打断标志

    }

    static void interruptPack() {
        Thread t3 = new Thread(() -> {
            System.out.println("park...");
            LockSupport.park();

            // 被打断前不会执行下面语句

            System.out.println("unpark...");

            System.out.println("打断状态1: " + Thread.currentThread().isInterrupted()); // 若被 Interrupt 打断，该白纸为true；若是被unpark唤醒，为false

            LockSupport.park();  // 若上一 pack 被 Interrupt 打断，这个park不会生效，且会直接执行下面的代码，因为有打断标志的存在。若想让这个pack生效可以把上面检查打断标志的方法换成interrupted。
            System.out.println("打断状态2: " + Thread.currentThread().isInterrupted());

            System.out.println("unpark...");
        }, "t3"); 
        
        t3.start();

        // t3.interrupt();  // 使用 Interrupt 打断线程后，线程的打断标志被设置为true，线程从park状态唤醒后不会清楚打断标志，若再次使用park不会生效
        LockSupport.unpark(t3);  // 使用unpark会让pack中的线程恢复，但不会影响线程的打断标志，线程还可以继续pack

    }
    
}

class SynInterruptTest {
    private synchronized void tt1() throws InterruptedException {
        System.out.println("tt1 is running...");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("tt1 was ended...");
    }

    private synchronized void tt2() {
        System.out.println("tt2 is running...");
    }

    /**
     * question: 线程被对象锁阻塞时，能否被interrupt打断; 答案：不会，参考test执行结果
     * test执行输出结果:
     *     tt1 is running...
     *     main: 子线程t2的状态: BLOCKED
     *     main: 子线程t2的打断标志: true
     *     tt1 was ended...
     *     t1: 子线程t1运行结束。。。
     *     tt2 is running...
     *     t2: 子线程t2的打断标志: true
     *     t2: 子线程t2运行结束。。。
     * @throws InterruptedException
     */
    public void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                tt1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1: 子线程t1运行结束。。。");
        });

        Thread t2 = new Thread(() -> {
            tt2();
            System.out.println("t2: 子线程t2的打断标志: " + Thread.currentThread().isInterrupted());
            System.out.println("t2: 子线程t2运行结束。。。");
        });

        t1.start();

        TimeUnit.SECONDS.sleep(1);  // 保证t2在t1之后运行
        t2.start();
        t2.interrupt(); // 打断t2
        System.out.println("main: 子线程t2的状态: " + t2.getState());
        System.out.println("main: 子线程t2的打断标志: " + t2.isInterrupted());
    }
}
