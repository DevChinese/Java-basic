package com.hua.collection.collection;

import java.util.HashSet;
import java.util.Set;

public class SetStudy {
    public static void main(String[] args) {
        Set<String> hashSet = new HashSet<String>();
        hashSet.add("sss");

        hashSet.add("aaaa");

        hashSet.forEach(t -> {
            System.out.println(t);
        });
        System.out.println(hashSet.contains("sss"));
    }

}
