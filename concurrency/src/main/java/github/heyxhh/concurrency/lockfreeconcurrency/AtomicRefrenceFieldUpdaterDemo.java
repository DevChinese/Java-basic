package github.heyxhh.concurrency.lockfreeconcurrency;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicRefrenceFieldUpdaterDemo {

    public static void main(String[] args) {
        Person p = new Person();

        // 先获取字段更新器
        AtomicReferenceFieldUpdater<Person, String> updater =
                AtomicReferenceFieldUpdater.newUpdater(Person.class, String.class, "name");

        updater.compareAndSet(p, null, "小王");


        System.out.println(p);
    }


}


class Person {
    // 属性不能是私有的
    volatile String name;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}