package github.heyxhh.concurrency.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.MyCustomizedThreadPool")
public class MyCustomizedThreadPool {

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(1, 1000,
                TimeUnit.MILLISECONDS, 1, (queue, task) -> {
            // 1. 阻塞等待
            // queue.put(task);
            // 2. 超时等待
            // queue.offer(task, 1500, TimeUnit.MILLISECONDS);
            // 3. 放弃执行
            // log.debug("放弃执行{}", task);
            // 4. 抛出异常
            // throw new RuntimeException("队列满了");
            // 5. 让调用者自己执行
            task.run();

        });

        for (int i = 0; i < 4; ++i) {
            pool.execute(() -> {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("xxxxxxxx");
            });
        }
    }

}

@FunctionalInterface
interface RejectPolicy<T> {

    void reject(BlockedQueue<T> queue, T task);

}


@Slf4j(topic = "c.ThreadPool")
class ThreadPool {
    // 任务队列
    private BlockedQueue<Runnable> waitingQueue;

    private RejectPolicy<Runnable> rejectPolicy;

    // 线程集合
    private HashSet<Worker> workers = new HashSet<>();

    // 核心线程数
    private int coreSize;

    // 获取任务的超市时间
    private long timeout;

    private TimeUnit timeUnit;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int waitingQueueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        waitingQueue = new BlockedQueue<>(waitingQueueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task) {
        // 当前任务数没有超过 coreSize，创建新的线程交给线程池执行
        // 当前任务数超过了 coreSize，把 task 加入任务队列
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                workers.add(worker);
                worker.start();
                log.debug("执行worker: {}, task: {}", worker, task);
            } else {
                 // waitingQueue.put(task);
                waitingQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    class Worker extends Thread {

        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        /**
         * 执行任务
         * 1）当task不为空，执行任务
         * 2）当task执行完毕，从等待队列中取出任务执行
         */
        @Override
        public void run() {

//            while (task != null || (task = waitingQueue.take()) != null) {
            while (task != null || (task = waitingQueue.poll(timeout, timeUnit)) != null) {
                try {
                    log.debug("执行task: {}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }

            }

            synchronized (workers) {
                log.debug("移除worker: {}", this);
                workers.remove(this);
            }

        }
    }


}


@Slf4j(topic = "c.BlockedQueue")
class BlockedQueue<T> {

    public BlockedQueue(int capacity) {
        this.capacity = capacity;
    }

    // 任务队列
    private Deque<T> queue = new ArrayDeque<>();

    // 锁
    private ReentrantLock lock = new ReentrantLock();

    // 生产者条件变量
    Condition fullWaitSet = lock.newCondition();

    // 消费者锁
    Condition emptyWaitSet = lock.newCondition();

    private int capacity;

    // 带超时的阻塞获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        long nanos = unit.toNanos(timeout);  // 将timeout转换为纳秒
        try {
            while (queue.isEmpty()) {  // 使用while而不是if，因为存在虚假唤醒的情形
                if (nanos <= 0) {  // 如果已经超时，直接返回null
                    return null;
                }
                try {
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t =  (T) queue.removeFirst();
            fullWaitSet.signal();
            return  t;
        } finally {
            lock.unlock();
        }

    }

    // 带超时的阻塞插入
    public boolean offer(T task, long timeout, TimeUnit timeUnit) {
        lock.lock();
        long nanos =   timeUnit.toNanos(timeout);
        try {
            while (queue.size() == capacity) {
                try {
                    if (nanos <= 0) {
                        log.debug("插入队列超时{}", task);
                        return false;
                    }
                    log.debug("等待加入任务队列{}", task);
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入等待队列{}...", task);
            queue.addLast(task);  // 一定要在循环外执行
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    // 阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {  // 使用while而不是if，因为存在虚假唤醒的情形
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t =  (T) queue.removeFirst();
            fullWaitSet.signal();
            return  t;
        } finally {
            lock.unlock();
        }
    }

    public void put(T task) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    log.debug("等待加入任务队列{}", task);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入等待队列{}...", task);
            queue.addLast(task);  // 一定要在循环外执行

            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 带有拒绝策略的插入
     * @param rejectPolicy 拒绝策略
     * @param task 任务
     */
    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            if (queue.size() == capacity) {
                rejectPolicy.reject(this, task);
            } else {
                log.debug("加入等待队列{}...", task);
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }

    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

}
