package github.heyxhh.lambda;

public class LambdaDemo {
    public static void main(String[] args) {
        UTest ut = new UTest("xiaoming");

        int a = 10;

        new Thread(() -> {
            ut.setName("hahah");  // 可以修改引用变量中的成员属性
            // a = 20; 局部栈变量在lambda中不能修改
            // ut = new UTest("hehe"); ut的引用不能修改
        });

        System.out.println(ut);

        ut.setName("xiaohong");

        System.out.println(ut);

    }
}

class UTest {
    private String name;

    public UTest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UTest{" +
                "name='" + name + '\'' +
                '}';
    }
}
