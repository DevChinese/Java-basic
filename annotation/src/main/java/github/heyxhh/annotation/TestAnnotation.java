package github.heyxhh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public class TestAnnotation {
    @Annotation01()
    void test01() {

    }

    @Annotation02("kaka")
    void test02() {}

    // @Annotation03("kaka") // 报错：Cannot find method 'value'
    @Annotation03(name = "kakaa1")
    void test03() {}

}


@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Annotation01 {
    int id() default -1;
    String name() default "";
    int age() default 18;

    String[] childs() default {"xiaohong", "xiaoming"};
}



@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Annotation02 {
    String value() default ""; // 当只有一个参数时，建议使用value作为参数，这样在使用是可以不用显示的说明参数名传参
}

@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Annotation03 {
    String name() default "";
}
