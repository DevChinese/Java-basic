package github.heyxhh.concurrency.sync.waitnotify;

import java.util.concurrent.TimeUnit;

public class WaitDemo {

    public static void main(String[] args) {

        new WaitDemo().testWaitProcess();;

        
        
    }


    /**
     * 被notify唤醒的线程再次获得锁后，是直接从wait语句的下一行代码开始执行吗？还是从synchronized除开始运行？
     *     答案：会从wait的下一句开始执行。从该方法的运行结果可以看到，每一个线程只会打印一次 before wait in sync
     *     被notifyAll唤醒后，waiting状态的线程全部被唤醒，获得锁的线程会接着wait语句的下一行代码继续运行
     *     没获得锁的线程进入blocked状态(注意: 是Blocked不是wating)，但是后续一旦获得锁，也会接着wait语句的下一行代码继续运行
     */
    public void testWaitProcess() {

        for(int i = 0; i < 20; ++i) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() +  ": before wait out sync。。。");
    
                synchronized(this) {
                    System.out.println(Thread.currentThread().getName() + ": before wait in sync。。。");
                    try {
                        this.wait();
                        System.out.println(Thread.currentThread().getName() + ": after wait in sync。。。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "child-" + i).start();;

        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized(this) {
            this.notifyAll();
        }

    }
    
}
