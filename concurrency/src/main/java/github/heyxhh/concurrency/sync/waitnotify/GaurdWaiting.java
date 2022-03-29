package github.heyxhh.concurrency.sync.waitnotify;

import java.util.concurrent.TimeUnit;

/**
 * 保护性暂停模式
 */
public class GaurdWaiting {

    public static void main(String[] args) {

        GaurdedObject gaurdedObject = new GaurdedObject();

        // 线程 t1 等待 线程 t2 的结果
        new Thread(() -> {
            System.out.println("t1: begein");
            Object rsp = gaurdedObject.get(2000L);
            System.out.println("t1: 获取的结果是: " + rsp);
        }, "t1").start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2: begein");
            gaurdedObject.produce(null);
            // gaurdedObject.produce(new String("hahahahah"));
        }, "t2").start();
        
    }
    
    
}


class GaurdedObject {

    // 结果
    private Object response;

    public Object get(Long timeout) {

        // 开始时间
        long timeBegin = System.currentTimeMillis();
        // 经历时间
        long passedTime = 0;

        // 写法跟join()的实现方式类似
        synchronized(this) {
            while(response == null) {
                // 这一轮循环应该等待的时间
                long waitTime = timeout - passedTime;
                // 经历的时间超过了最大等待时间，退出循环
                if (waitTime <= 0) {
                    break;
                }
                
                try {
                    wait(waitTime);  // 因为可能存在被打断或者虚假唤醒的情况，因此不能直接使用get函数传进来的那个timeout
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                passedTime = System.currentTimeMillis() - timeBegin;
            }
        }

        return response;
    }

    public void produce(Object response) {

        synchronized(this) {
            this.response = response;

            notifyAll();
        }

    }

    
}