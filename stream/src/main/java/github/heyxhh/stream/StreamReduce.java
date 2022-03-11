package github.heyxhh.stream;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 上一节介绍了部分Stream常见接口方法，理解起来并不困难，但Stream的用法不止于此，本节我们将仍然以Stream为例，介绍流的规约操作。
 *
 * 规约操作（reduction operation）又被称作折叠操作（fold），是通过某个连接动作将所有元素汇总成一个汇总结果的过程。
 * 元素求和、求最大值或最小值、求出元素总个数、将所有元素转换成一个列表或集合，都属于规约操作。Stream类库有两个通用的规约操作reduce()和collect()，也有一些为简化书写而设计的专用规约操作，比如sum()、max()、min()、count()等。
 * 最大或最小值这类规约操作很好理解（至少方法语义上是这样），我们着重介绍reduce()和collect()，这是比较有魔法的地方。
 */
public class StreamReduce {
    public static void main(String[] args) {
        // getLongestStr();

        getSumLength();
    }

    public static void getLongestStr() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        Optional<String> longest = stream.reduce((s1, s2) -> s1.length() >= s2.length() ? s1 : s2);
        // Optional<String> longest = stream.max((s1, s2) -> s1.length()-s2.length());
        System.out.println(longest.get());
    }

    public static void getSumLength() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        int len = stream.reduce(
                0,  // 初始值　// (1)
                (sum, str) -> sum += str.length(),  // 累加器 // (2)
                (a, b) -> a + b  // 部分和拼接器，并行执行时才会用到 // (3)
        );
        // int lengthSum = stream.mapToInt(str -> str.length()).sum();
        System.out.println(len);
    }
}
