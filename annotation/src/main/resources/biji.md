# 注解

## 注解可以放在那里？
- 注解可以附加在package、class、method、field等上面
- 可以给他们添加了额外的辅助信息，我们可以通过反射机制编程实现对这些元数据的访问。

## 一些常见的内置注解
- @Override
- @Deprecated
- @SuppressWarnings
- 等

## 元注解
元注解可以注解其他注解，Java定义了4个标注的meta-annotation类型，他们被用来提供对
其他annotation类型作说明。

这些类型和他们所支持的类在java.lang.annotation包中可以找到。分别是：@Target、@Retention、
@Documented、@inherited
- @Target：用于描述注解使用的范围（即被描述的注解可以用在什么地方）
- @Retention：表示需要在什么级别保存该注释信息，用于描述注解的生命周期（source < class < runtime）
- @Documented：说明该注解将被包含在文档中
- @inherited：说明子类可以继承父类中的该注解

## 自定义注解
使用@interface定义注解时，自动继承了java.lang.annotation.Annotation接口
分析:
- @interface用来声明一个注解，格式：public @interface 注解名 {定义内容}
- 注解中每一个方式实际上是声明了一个配置参数
- 方法的名称就是参数的名称
- 方法的返回值类型就是参数的类型（返回值只能是基本类型：Class、String、Enum）
- 可以通过default来声明参数的默认值
- 如果只有一个参数成员，一般把参数名定义为 value
- 注解元素必须要有值，我们定义注解元素时，经常使用空字符串/0作为默认值。

