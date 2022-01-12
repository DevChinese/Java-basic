package com.hua.stream;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCollect {
    public static void main(String[] args) {
        collectExample();
    }

    /**
     * 将Stream转换成容器或Map
     */
    public static void collectExample() {
        Stream<String> stream1 = Stream.of("I", "love", "you", "too");
        List<String> list = stream1.collect(Collectors.toList());  // (1)
        list.forEach(System.out::println);

        // 注意不能在复用上面的stream1，stream用过一次终止函数后便失效了
        Stream<String> stream2 = Stream.of("I", "love", "you", "too");
        Map<String, Integer> map = stream2.collect(Collectors.toMap(Function.identity(), String::length));  // (2)
        // 提示：map和set的输出，不保证维持元素加入的顺序
        map.forEach((k, v) -> System.out.println(k + ": " + v));

        Stream<String> stream3 = Stream.of("I", "love", "you", "too");
        Set<String> set = stream3.collect(Collectors.toSet());  // (3)
        set.forEach(System.out::println);
    }
}
