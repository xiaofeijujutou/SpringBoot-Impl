package com.xiaofei.framework.beans.factory;

/**
 * @Description: Created by IntelliJ IDEA.
 * bean对象实现了这个接口,在bean初始化好之后,
 * 就会把这个bean在容器中的名字传过来
 * @Author : 小肥居居头
 * @create 2024/3/17 21:20
 */


public interface BeanNameAware extends Aware{
    /**
     * 在bean初始化好之后,就会把这个bean在容器中的名字传过来;
     * @param beanName bean在容器中的名字;
     * @return
     */
    void setBeanName(String beanName);
}
