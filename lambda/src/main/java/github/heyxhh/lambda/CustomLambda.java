package github.heyxhh.lambda;

import java.util.ArrayList;
import java.util.List;

public class CustomLambda {
    public static void main(String[] args) {
        // 有了接口定义，就可以写出类似如下的代码：
        ConsumerInterface<String> consumer = str -> System.out.println(str);
        consumer.accept("1111111111");

        // 进一步的，还可以这样使用
        MyStream<String> myStream = new MyStream<String>();
        myStream.addItem("22222");
        myStream.addItem("33333");
        myStream.myForEach(t -> System.out.println(t));  // 使用自定义函数接口书写Lambda表达式

        // 测试函数式接口中的非抽象方法
        ConsumerInterface<String> cc = t -> System.out.println(t);
        cc.notAbstract();
        ConsumerInterface.staticFunc();



    }
}

/**
 * 自定义函数接口很容易，只需要编写一个只有一个抽象方法的接口即可。
 * 注解@FunctionalInterface是可选的，但加上该标注编译器会帮你检查接口是否符合函数接口规范。就像加入@Override标注会检查是否重载了函数一样。
 * 注意：函数式接口中只有一个抽象方法，但不限制存在其他非抽象方法
 * @param <T>
 */
@FunctionalInterface
interface ConsumerInterface<T> {
    void accept(T t);

    default void notAbstract() {
        System.out.println("notAbstract");
    }

    static void staticFunc() {
        System.out.println("staticFunc");
    }
}

class MyStream<T> {
    private List<T> list;

    MyStream() {
        list = new ArrayList<T>();
    }

    public void addItem(T t) {
        list.add(t);
    }

    public void myForEach(ConsumerInterface<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }
}