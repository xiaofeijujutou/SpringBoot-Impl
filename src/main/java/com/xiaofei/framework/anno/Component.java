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

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    //bean的名字
    String value() default "";
}
