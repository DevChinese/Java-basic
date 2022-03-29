package github.heyxhh.concurrency.sync.waitnotify;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class MessageQueueDemo {

    public static void main(String[] args) {

        MessageQueue messageQueue = new MessageQueue(2);

        // 三个生产者线程
        for(int i = 0; i < 3; ++i) {
            int id = i;
            new Thread(() -> {
                messageQueue.produce(new Message(id, "消息i: " + id));
            }, String.valueOf(i)).start();;
        }
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 消费者
        while(true) {
            messageQueue.take();
        }
    }

}

final class MessageQueue {

    private final LinkedList<Message> queue = new LinkedList<>();

    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    // 获取消息
    public Message take() {

        synchronized(queue) {
            // 检测队列是否为空
            while(queue.isEmpty()) {
                try {
                    System.out.println("队列为空。。。");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Message message = queue.removeFirst();
            System.out.println("消费一个消息: " + message);
            // 唤醒等待中的生产者线程
            queue.notifyAll();
            return message;
        }
    }

    public void produce(Message message) {
        synchronized(queue) {
            while(queue.size() == capacity) {
                try {
                    System.out.println("队列满了。。。");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            queue.addLast(message);
            System.out.println("生产了一个消息: " + message);
            queue.notifyAll();
        }
    }
    
}

class Message {
    private int id;
    private Object message;

    public Message(int id, Object message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", message=" + message + "]";
    }

    
}
