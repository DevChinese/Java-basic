package github.heyxhh.concurrency.threadstatus;

import java.util.concurrent.TimeUnit;

public class TwoPhaseTerminationDemo {

    public static void main(String[] args) {

        TwoPhaseTermination th = new TwoPhaseTermination();
        th.start();

        try {
            TimeUnit.SECONDS.sleep(1);
            th.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
  
}

class TwoPhaseTermination {
    private Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted() && true) {
                System.out.println("健康检查...");

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    // 需要再次设置打断标志，因为sleep时被interrupt唤醒会清空打断标志
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

            System.out.println("善后...");
        });

        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }
    
}
