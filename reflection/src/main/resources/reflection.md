# reflection

## 基本概念
说白了，反射就是获取类的元信息：该类所对应的Class对象。
通过类的Class对象可以获取到该类具有哪些属性、具备哪些方法，包括私有属性和方法。
可通过class对象，生成该类的实例，也可以通过反射调用该类的方法。

## Class类
类的Class实例包含了这个类的属性、方法、构造器、实现了哪些接口等。
对应每一个类，JRE都为其保留了一个不变的Class类型的对象。
有如下概念：
- Class本身也是一个类
- 一个加载的类在JVM中只会存在一个Class对象
- 一个Class对象对应被加载到JVM中的一个.class文件
- 每个类的实例都会记得自己是由哪个Class实例所生成的
- 通过Class实例可以完整的获得一个类的结构

## 获取Class实例的方法
假设有一个类Persion
    1、方式一：若已知具体的类，通过类的class属性获取，该方法最为可靠，程序性能最高。
        Class c1 = Persion.class;
    2、方式二：通过class.forName()获得
        Class c2 = Class.forName("com.xxx.Person"); // 注意要使用完整的报名
    3、方式三：通过obj.getClass()获得
        obj = Person();
        Class c3 = obj.getClass();

获取基本数据类型的Class实例，基本数据类型都有一个TYPE属性
    class c = Integer.TYPE;
    System.out.println(cc); // int
    

通过Class实例可以获得父类的Class实例
    Class c1 = Persion.class;
    Class c2 = c1.getSupperClass(); // Person父类的Class实例
    


