package github.heyxhh.concurrency.jmm;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

public class TwoPhaseTerminationUsingVolatile {
    
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt =  new TwoPhaseTermination();

        tpt.monitor();

        tpt.monitor();  // 不会再次启动一个 monitor 线程

        TimeUnit.SECONDS.sleep(5);

        tpt.stop();
    }

}

@Slf4j(topic = "c.TwoPhaseTerminationUsingVolatile")
class TwoPhaseTermination {

    // 最好把stop声明为 volatile，否则其他线程执行 stop 方法后，monitor 线程可能不会立马停止监控
    private volatile boolean stop = false;

    // 必须使用 锁 来保证一致性，因为可能有多个线程修改 staring，必须要保证原子性; 而上面的 stop 只有一个线程会修改，保证可见性即可
    private boolean staring = false;  // 保证重复调用 monitor 方法时，只开启一个 monitor 线程

    public void monitor() {

        synchronized(this) {
            if(staring) {
                return;
            }
            staring = true;
        }

        Thread monitor = new Thread(() -> {
            while (!stop) {
                log.debug("健康检查...");

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    // 需要再次设置打断标志，因为sleep时被interrupt唤醒会清空打断标志
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

            log.debug("善后...");
        }, "monitor");

        monitor.start();
    }

    public void stop() {
        stop = true;
    }
    
}