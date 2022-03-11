package github.heyxhh.reflection;

public class BasicIdea {
    void someWayGetClassObj() {

    }


    public static void main(String[] args) throws ClassNotFoundException {
        // 知识点：一个类只存在一个Class对象
        Class c1 = Class.forName("edu.haihua.reflection.User");
        System.out.println(c1); // class edu.haihua.reflection.User
        Class c2 = Class.forName("edu.haihua.reflection.User");
        System.out.println(c1.hashCode() == c2.hashCode()); // true
        // 数组：只有元素类型与维度一样，数组的Class实例都是一样的，只存在一个
        String[] s1 = new String[10];
        String[] s2 = new String[100];
        Class c3 = s1.getClass();
        Class c4 = s1.getClass();
        System.out.println(c3.hashCode() == c4.hashCode()); // true

    }

}

class User {
    int id;
    String name;
    int age;

    // TODO: 包级别的类中，定义public方法，在别的包中能访问到吗？
    public User() {};

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
