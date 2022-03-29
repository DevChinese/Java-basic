package github.heyxhh.concurrency.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeAccessor {
    private static final Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new Error(e);  // 必须抛出，否则声明 unsafe 的地方会报错: Variable 'unsafe' might not have been initialized
            // throw new RuntimeException(e); 抛出这个异常也可
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }


}
