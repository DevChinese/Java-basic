package github.heyxhh.concurrency.lockfreeconcurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LongAdderDemo {
    public static void main(String[] args) throws InterruptedException {

        demo(
                () -> new LongAdder(),
                adder -> adder.increment()
        );


    }

    private static <T> void demo(Supplier<T> addSupplier, Consumer<T> action) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        T adder = addSupplier.get();

        for (int i = 0; i < 100; ++i) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 100; ++j)
                action.accept(adder);
            });

            threads.add(t);

            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println(adder);
    }
}

