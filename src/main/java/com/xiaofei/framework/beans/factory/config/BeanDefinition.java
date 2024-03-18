package com.xiaofei.framework.beans.factory.config;

import com.sun.istack.internal.Nullable;
import com.xiaofei.framework.beans.factory.BeanPostProcessor;

/**
 * @Description: Created by IntelliJ IDEA.
 * @Author : 小肥居居头
 * @create 2024/3/18 12:15
 */


public interface BeanDefinition {
    //单例或原型的常量;
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 设置类加载器;
     * @param beanClassLoader 类加载器
     */
    void setBeanClassLoader(@Nullable ClassLoader beanClassLoader);

    /**
     * 获取类加载器
     * @return 类加载器
     */
    @Nullable
    ClassLoader getBeanClassLoader();

    /**
     * 添加后置处理器(暂时不知道怎么用);
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 获取bean的所有依赖的名字
     * @param beanName
     * @return
     */
    String[] getDependentBeans(String beanName);

    /**
     * 设置Bean的全类名
     * @param beanClassName
     */
    void setBeanClassName(@Nullable String beanClassName);

    /**
     * 获取Bean的全类名
     * @return
     */
    String getBeanClassName();

    /**
     * 设置Bean为单例还是原型;
     * @param scope
     */
    void setScope(@Nullable String scope);

    /**
     * 获取Bean为单例还是原型
     * @return
     */
    @Nullable
    String getScope();


}
