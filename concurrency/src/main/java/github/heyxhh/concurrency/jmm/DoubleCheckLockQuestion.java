package github.heyxhh.concurrency.jmm;


public class DoubleCheckLockQuestion {
    
}


class DoubleCheckDemo {

    private static volatile DoubleCheckDemo INSTANCE = null;

    public static DoubleCheckDemo singleton() {

        if(INSTANCE == null) {
            // 首次访问时会同步，而之后不会
            synchronized(DoubleCheckDemo.class) {
                if(INSTANCE == null) {
                    INSTANCE = new DoubleCheckDemo();
                }
            }
        }

        return INSTANCE;
    }

}


// 推荐一中懒汉单例实现模式
final class Singleton { 
    private Singleton() {}

    // 问题1:属于懒汉式还是饿汉式？
    // 属于懒汉时，因为只有该类被使用时才会被JVM加载
    private static class LazyHolder {
        static final Singleton INSTANCE = new Singleton(); 
    }

    // 问题2:在创建时是否有并发问题
    // 不会，因为静态变量有JVM加载，JVM会保证
    public static Singleton getInstance() {
        return LazyHolder.INSTANCE; 
    }
}