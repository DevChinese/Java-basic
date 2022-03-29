package github.heyxhh.concurrency.threadstatus;


/**
 * 默认情况下，Java 进程需要等待所有线程都运行结束，才会结束。
 * 有一种特殊的线程叫做守护线程，只要其它非守护线程运行结束了，即使守护线程的代码没有执行完，也会强制结束。
 * 
 * 垃圾回收器线程就是一种守护线程
 * Tomcat 中的 Acceptor 和 Poller 线程都是守护线程，所以 Tomcat 接收到 shutdown 命令后，不会等待它们处理完当前请求。
 */
public class DaemonThreadDemo {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while(true) {
                System.out.println("Daemon线程运行中。。。");
            }
        }, "Daemon");

        t1.setDaemon(true);  // 将t1设置为守护进程; 一定要做start调用前设置

        t1.start();
    }
    
}
