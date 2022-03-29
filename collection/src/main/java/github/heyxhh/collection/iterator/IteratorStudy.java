package github.heyxhh.collection.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IteratorStudy {

    public static void main(String[] args) {
        List<Person> l = new ArrayList<>();
        
        Person p1 = new IteratorStudy.Person("aa", 16);
        Person p2 = new Person("bb", 18);

        l.add(p1);
        l.add(p2);
        
        System.out.println(l);

        p1.setAge(20);

        System.out.println(l);

        HashSet<Person> h = new HashSet<>();
        h.add(p1);
        h.add(p2);
        System.out.println(h);
        p2.setAge(30);
        System.out.println(h);
        h.remove(p2);
        System.out.println(h);
    }


    static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Person other = (Person) obj;
            if (age != other.age)
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Person [age=" + age + ", name=" + name + "]";
        }

        

        
        
    }


}
