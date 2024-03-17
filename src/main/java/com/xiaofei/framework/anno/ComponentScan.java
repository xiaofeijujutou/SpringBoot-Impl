package com.xiaofei.framework.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: Created by IntelliJ IDEA.
 * 扫包注解;
 * @Author : 小肥居居头
 * @create 2024/3/17 16:48
 */

@Retention(RetentionPolicy.RUNTIME) //程序运行的时候能被发现;
@Target(ElementType.TYPE) //标识下面这个注解只能加在类上;
public @interface ComponentScan {
    //注解里面包含一个String类型的值
    //使用方法:@ComponentScan(value = "com.xiaofei")
    //方法名就是属性名
    String value() default "";
}
