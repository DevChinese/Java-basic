package github.heyxhh.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamBase {
    public static void main(String[] args) {
        // steamForEach();
        // streamFilter();
        // streamDistinct();
        // streamSorted();
        // streamMap();
        streamFlatMap();
    }

    public static void steamForEach() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        // forEach()的函数签名是void forEach(Consumer<? super E> action)
        // forEach()是结束方法，代码会立即执行，输出所有字符串。
        stream.forEach(str -> System.out.println(str));
    }

    public static void streamFilter() {
        // filter函数原型为Stream<T> filter(Predicate<? super T> predicate)
        // 作用是返回一个只包含满足predicate条件元素的Stream。
        // 注意：由于filter()是个中间操作，如果只调用filter()不会有实际计算，因此也不会输出任何信息。
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        stream.filter(str -> str.length() == 3).forEach(str -> System.out.println(str));
    }

    public static void streamDistinct() {
        // 函数原型为Stream<T> distinct()，作用是返回一个去除重复元素之后的Stream
        Stream<String> stream = Stream.of("I", "love", "you", "too", "too", "love");
        stream.distinct().forEach(str -> System.out.println(str));
    }

    public static void streamSorted() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        // 排序函数有两个，一个是用自然顺序排序，一个是使用自定义比较器排序，
        // 函数原型分别为Stream<T>　sorted()和Stream<T>　sorted(Comparator<? super T> comparator)。
        stream.sorted((str1, str2) -> str1.length() - str2.length()).forEach(System.out::println);
    }

    public static void streamMap() {
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        // 函数原型为<R> Stream<R> map(Function<? super T,? extends R> mapper)，
        // 作用是返回一个对当前所有元素执行执行mapper之后的结果组成的Stream。
        // 直观的说，就是对每个元素按照某种操作进行转换，转换前后Stream中元素的个数不会改变，但元素的类型取决于转换之后的类型。
        stream.map(str -> str.toUpperCase())
                .forEach(System.out::println);
    }

    public static void streamFlatMap() {
        Stream<List<Integer>> stream = Stream.of(Arrays.asList(1, 3), Arrays.asList(2,5));
        // stream.flatMap 函数原型为<R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)，
        // 作用是对每个元素执行mapper指定的操作，并用所有mapper返回的Stream中的元素组成一个新的Stream作为最终返回结果。
        // 说起来太拗口，通俗的讲flatMap()的作用就相当于把原stream中的所有元素都”摊平”之后组成的Stream，转换前后元素的个数和类型都可能会改变。
        stream.flatMap(list -> list.stream()).forEach(System.out::println);
    }
}
