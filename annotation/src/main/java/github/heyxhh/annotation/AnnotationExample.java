package github.heyxhh.annotation;

import java.lang.annotation.*;

/**
 * @Target：表示注解可以用在什么地方
 * @Retention：表示注解在什么阶段有效，一般使用RUNTIME
 * @Documented: 表示直接会被包含在Java Doc中
 * @Inherited：表示子类可以继承父类的注解
 */
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AnnotationExample {
}
