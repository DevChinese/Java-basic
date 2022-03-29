package github.heyxhh.concurrency.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe并不是说该类线程不安全，而是因为该类比较底层，容易出错
 */
public class UnsafeUsingDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");

        theUnsafe.setAccessible(true);  // 因为 theUnsafe 是个 private 成员，需要设置访问权限
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);  // 因为 theUnsafe 是个静态字段，不需要传一个对象

        System.out.println(unsafe);

        // 获取域的偏移
        long idOffset = unsafe.objectFieldOffset(TT.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(TT.class.getDeclaredField("name"));

        TT tt = new TT();

        unsafe.compareAndSwapInt(tt, idOffset, 0, 1);
        unsafe.compareAndSwapObject(tt, nameOffset, null, "kakaka");

        System.out.println(tt);


    }
}

class TT {
    volatile int id;
    volatile String name;

    @Override
    public String toString() {
        return "TT{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
