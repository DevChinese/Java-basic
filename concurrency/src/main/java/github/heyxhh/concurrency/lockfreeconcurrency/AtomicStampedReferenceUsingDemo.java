package github.heyxhh.concurrency.lockfreeconcurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 主线程仅能判断出共享变量的值与最初值 A 是否相同，不能感知到这种从 A 改为 B 又 改回 A 的情况，如果主线程 希望:
 * 只要有其它线程【动过了】共享变量，那么自己的 cas 就算失败，这时，仅比较值是不够的，需要再加一个版本号
 */
@Slf4j(topic = "c.AtomicStampedReferenceUsingDemo")
public class AtomicStampedReferenceUsingDemo {

    private static AtomicStampedReference<User> ref = new AtomicStampedReference<>(new User("A", 18), 0);

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");
        User lastRef = ref.getReference();
        int lastStamp = ref.getStamp();
        log.debug("stamp: {}", lastStamp);

        User cur = new User("C", 19);

        ABA();

        TimeUnit.SECONDS.sleep(1);

        log.debug("change A->C 是否成功: {}", ref.compareAndSet(lastRef, cur, lastStamp, lastStamp+1));
    }

    private static void ABA() throws InterruptedException {
        new Thread(() -> {
            log.debug("thread t1 start...");
            User lastRef = ref.getReference();
            int lastStamp = ref.getStamp();
            log.debug("stamp: {}", lastStamp);

            User cur = new User("B", 16);

            log.debug("t1-1: {}", lastRef.hashCode());

            log.debug("change A->B 是否成功: {}", ref.compareAndSet(lastRef, cur, lastStamp, lastStamp+1));

            log.debug("t1-2: {}", ref.getReference().hashCode());  // 跟t1-1输出的值是不一样的

        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            log.debug("thread t2 start...");
            User lastRef = ref.getReference();
            int lastStamp = ref.getStamp();
            log.debug("stamp: {}", lastStamp);

            User cur = new User("A", 16);

            log.debug("change B->A 是否成功: {}", ref.compareAndSet(lastRef, cur, lastStamp, lastStamp+1));
        }, "t2").start();
    }
}


class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}