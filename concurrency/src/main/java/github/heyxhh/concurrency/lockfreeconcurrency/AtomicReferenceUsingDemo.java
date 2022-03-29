package github.heyxhh.concurrency.lockfreeconcurrency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceUsingDemo {

    public static void main(String[] args) throws InterruptedException {

        DecimalAccountCas tt = new DecimalAccountCas(new BigDecimal(10000));

        tt.testDemo();
        
    }

    
}

class DecimalAccountCas implements DecimalAccount {

    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal amount) {
        balance = new AtomicReference<BigDecimal>(amount);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withDraw(BigDecimal amount) {

        while (true) {
            // BigDecimal 是不可变类型，是线程安全的，但是 当多个操作组合时，不是原子的，不能保证线程安全，因此需要使用Atomic
            BigDecimal pre = balance.get();
            BigDecimal next = pre.subtract(amount);
            if (balance.compareAndSet(pre, next)) {
                break;
            }
        }

        // balance.updateAndGet(x -> x.subtract(amount));
        
    }

}

interface DecimalAccount {
    // 获取余额
    BigDecimal getBalance();

    // 取钱
    void withDraw(BigDecimal amount);

    default void testDemo() throws InterruptedException {

        List<Thread> list = new ArrayList<>();

        for(int i = 0; i < 1000; i++) {
            Thread t = new Thread(() -> {
                withDraw(new BigDecimal(10));
            });

            list.add(t);

            t.start();
        }

        for(Thread t : list) {
            t.join();
        }

        System.out.println(getBalance());
    }
}