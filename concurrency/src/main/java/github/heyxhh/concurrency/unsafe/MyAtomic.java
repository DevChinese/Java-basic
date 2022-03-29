package github.heyxhh.concurrency.unsafe;

import sun.misc.Unsafe;

import java.util.ArrayList;

public class MyAtomic {
    public static void main(String[] args) throws InterruptedException {
        MyAtomicInteger value = new MyAtomicInteger(0);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            Thread t = new Thread(value::add);

            threads.add(t);

            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println(value.getValue());
    }
}

class MyAtomicInteger {
    private volatile int value;

    private static final Unsafe unsafe;
    private static final long valueOffset;

    static {
        unsafe = UnsafeAccessor.getUnsafe();

        try {
            valueOffset =  unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void add() {
        // 不能写在这儿，因为进入下一轮while循环的时候，value的值可能已经变了
//        int pre = getValue();
//        int next = pre + 1;
        while (true) {
            if (unsafe.compareAndSwapInt(this, valueOffset, value, value + 10)) {
                break;
            }
        }
    }

}