package com.xiaofei.framework.beans.factory;

/**
 * @Description: Created by IntelliJ IDEA.
 * 对于Bean创建前后进行的操作
 * 成对出现,之前调用before,生成的obj存入一个容器,然后遍历容器,调用after;
 * @Author : 小肥居居头
 * @create 2024/3/17 21:38
 */


public interface BeanPostProcessor {

    /**
     * bean初试化之前
     * @param bean bean的Obj
     * @param beanName bean名字
     * @return
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * bean初始化之后
     * @param bean bean的Obj
     * @param beanName bean名字
     * @return
     */
    Object postProcessAfterInitialization(Object bean, String beanName);

}