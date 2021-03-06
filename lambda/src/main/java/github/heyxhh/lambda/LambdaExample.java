package github.heyxhh.lambda;


import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * 本节将介绍如何使用Lambda表达式简化匿名内部类的书写，
 * 但Lambda表达式并不能取代所有的匿名内部类，只能用来取代函数接口（Functional Interface）的简写。
 * 先别在乎细节，看几个例子再说。
 * TODO: lambda中的类型推倒方法
 */
public class LambdaExample {
    public static void main(String[] args) {
        lambdaNoArg();
    }

    /**
     * 无参函数的lambda
     */
    public static void lambdaNoArg() {
        // 如果需要新建一个线程，一种常见的写法是这样：
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Anonymous class thread run()");
            }
        });
        thread1.start();  // 必须要执行start方法，才会执行启动线程

        // 上述代码给Tread类传递了一个匿名的Runnable对象，重载Runnable接口的run()方法来实现相应逻辑。
        // 这是JDK7以及之前的常见写法。匿名内部类省去了为类起名字的烦恼，但还是不够简化，在Java 8中可以简化为如下形式：
        Thread thread2 = new Thread(
                () -> System.out.println("Lambda one line thread run()")
        );
        thread2.start();

        // 上述代码跟匿名内部类的作用是一样的，但比匿名内部类更进一步。这里连接口名和函数名都一同省掉了，写起来更加神清气爽。
        // 如果函数体有多行，可以用大括号括起来，就像这样：
        Thread thread3 = new Thread(
                () -> {
                    System.out.println("Lambda one line thread run() -- 01");
                    System.out.println("Lambda one line thread run() -- 02");
                }
        );
        thread3.start();
    }

    /**
     * 有参函数的lambda
     */
    public static void lambdaWithArg() {
        List<String> list = Arrays.asList("I", "love", "you", "too");

        // 如果要给一个字符串列表通过自定义比较器，按照字符串长度进行排序，Java 7的书写形式如下：
        Collections.sort(list, new Comparator<String>() { // 接口名
            @Override
            public int compare(String s1, String s2) { // 方法名
                if(s1 == null)
                    return -1;
                if(s2 == null)
                    return 1;
                return s1.length()-s2.length();
            }
        });

        // 上述代码通过内部类重载了Comparator接口的compare()方法，实现比较逻辑。
        // 采用Lambda表达式可简写如下：
        // 上述代码跟匿名内部类的作用是一样的。
        // 除了省略了接口名和方法名，代码中把参数表的类型也省略了。
        // 这得益于javac的类型推断机制，编译器能够根据上下文信息推断出参数的类型，当然也有推断失败的时候，这时就需要手动指明参数类型了。
        // 注意，Java是强类型语言，每个变量和对象都必需有明确的类型。
        Collections.sort(list, (s1, s2) -> {  // 省略了参数的类型
            if(s1 == null)
                return -1;
            if(s2 == null)
                return 1;
            return s1.length()-s2.length();
        });
    }

    /**
     * 能够使用Lambda的依据是必须有相应的函数接口（函数接口，是指内部只有一个抽象方法的接口）。
     * 这一点跟Java是强类型语言吻合，也就是说你并不能在代码的任何地方任性的写Lambda表达式。实际上Lambda的类型就是对应函数接口的类型。
     * Lambda表达式另一个依据是类型推断机制，在上下文信息足够的情况下，编译器可以推断出参数表的类型，而不需要显式指名。
     * Lambda表达更多合法的书写形式如下：
     */
    public static void otherIntro() {
        // Lambda表达式的书写形式
        Runnable run = () -> System.out.println("Hello World"); // 1 无参函数的简写
        ActionListener listener = event -> System.out.println("button clicked"); // 2 有参函数的简写,以及类型推断机制
        Runnable multiLine = () -> {// 3 代码块
            System.out.print("Hello");
            System.out.println(" Hoolee");
        };
        BinaryOperator<Long> add = (Long x, Long y) -> x + y;// 4
        BinaryOperator<Long> addImplicit = (x, y) -> x + y;// 5 类型推断
    }
}
