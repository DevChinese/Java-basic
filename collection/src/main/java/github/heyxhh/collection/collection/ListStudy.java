package github.heyxhh.collection.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @Description: 主要涉及List的内容
 * List接口的主要实现类有ArrayList和ListedList；
 * ArrayList内部封装了一个长度可变的数组对象（初始容量为8，按1.5倍扩容）
 * LinkedList，该集合内有一个双向循环链表，增删效率较高。
 */
public class ListStudy {
    public static void main(String[] args) {
//        retrieveArrayList();
        retieveLinkedList();
    }


    public static void retrieveArrayList() {
        List<String> arrList = new ArrayList<String>();
        // 增加元素
        arrList.add("1");
        arrList.add("2");
        arrList.add("3");

        // 删除元素
        arrList.remove("3");

        for (String item : arrList) {
            System.out.println(item);
        }

        Iterator<String> it = arrList.iterator();

        it.next();
        it.remove(); // 执行迭代器的删除方法前，一定要先执行next方法，remove删除的是上一次next方法返回的元素

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void retieveLinkedList() {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        linkedList.add(2);
        linkedList.add(3);

        for (Integer integer : linkedList) {
            integer = 3; // 修改后，不会影响容器里面的元素的值
            System.out.println(integer);
        }

        linkedList.forEach((t) -> {
            System.out.println(t);
        });
    }

}
