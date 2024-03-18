package com.xiaofei.framework.context.support;

import com.sun.istack.internal.Nullable;
import com.xiaofei.framework.beans.factory.support.BeanDefinitionReader;
import com.xiaofei.framework.beans.factory.support.BeanDefinitionRegistry;
import com.xiaofei.framework.context.ApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: Created by IntelliJ IDEA.
 * ApplicationContext接口实现子类,作用是将BeanFactory的懒加载改成立即加载;
 * @Author : 小肥居居头
 * @create 2024/3/14 19:01
 */


public abstract class AbstractApplicationContext implements ApplicationContext {

    /**
     * 依赖了Reader,也就间接依赖了Register;
     */
    protected BeanDefinitionReader beanDefinitionReader;

    /**
     * 单例Bean容器,这个需要三个map,也就是三级缓存;
     */
    protected Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    /**
     * 二级缓存
     */

    protected final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
    /**
     * 三级缓存
     */
    //private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);


    /**
     * 记录所有的至少被创建过一次的bean;
     */
    private final Set<String> alreadyCreated = Collections.newSetFromMap(new ConcurrentHashMap<>(256));
    /**
     * 抽象类必须要传入BeanDefinitionReader;
     * @param beanDefinitionReader
     */
    public AbstractApplicationContext(BeanDefinitionReader beanDefinitionReader) {
        this.beanDefinitionReader = beanDefinitionReader;
    }

    /**
     * 根据beanDefinitionReader生成了静态BeanDefinition;
     * 通过调用getBean去生成内存的实体对象;
     * @throws Exception
     */
    protected void finishBeanInitialization() throws Exception {
        //从注册表里面读取出注册信息;
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            //beanFactory的方法,调用了get就会去创建;
            //循环调用抽象方法,实际调用子类实现方法;
            getBean(beanDefinitionName);
        }
    }

    /**
     * spring从三级缓存中找bean;
     * @param beanName
     * @return
     */
    @Nullable
    protected Object getSingleton(String beanName) {
        //一级缓存里面找;
        Object singletonObject = this.singletonObjects.get(beanName);
        //二级缓存里面找;
        if (singletonObject == null && this.earlySingletonObjects.containsKey(beanName)) {
            //一级缓存为空,但是二级缓存有,那就赋值;
            singletonObject = this.earlySingletonObjects.get(beanName);
        }
        return singletonObject;
    }
}
