package github.heyxhh.concurrency.way2create;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * TODO: 补充Future和Callable相关知识
 */

public class FutureTaskDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> f = new FutureTask<>(() -> new String("Thread is running..."));

        Thread t = new Thread(f);

        t.start();

        String rst = f.get();  // get方法会阻塞等待结果返回

        System.out.println(rst);
    }
    
}
