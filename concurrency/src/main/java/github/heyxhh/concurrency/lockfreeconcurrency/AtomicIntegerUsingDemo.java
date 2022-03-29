package github.heyxhh.concurrency.lockfreeconcurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用银行转账的一个demo演示一个无锁并发的例子
 * CAS 和 volatile 搭配使用
 */
public class AtomicIntegerUsingDemo {
    public static void main(String[] args) throws InterruptedException {

        Account account = new AccountCas(10000);

        List<Thread> list = new ArrayList<>();

        for(int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                account.withDraw(10);
            });

            list.add(t);

            t.start();
        }

        for(Thread t : list) {
            t.join();
        }

        System.out.println(account.getBalance());
        
    }
 
}

interface Account {
    // 获取余额
    int getBalance();

    // 取钱
    void withDraw(int amount);
}


class AccountCas implements Account {

    private AtomicInteger balance;  // AtomicInteger中有个 private volatile int value; 来保存值，通过volatile来保证可见性

    public AccountCas(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public int getBalance() {
        return balance.get();
    }

    @Override
    public void withDraw(int amount) {

        // 虽然使用了循环，但是无锁特征，线程不会因为拿不到锁而阻塞，减少了上下文切换的次数，提升了性能
        // while(true) {
        //     // 获取余额的最新值
        //     int pre = balance.get();
        //     // 修改后的余额
        //     int next = pre - amount;
        //     // 修改余额
        //     if(balance.compareAndSet(pre, next)) {
        //         break;
        //     }
        // }  
        
        balance.addAndGet(-1 * amount);
    }

}
