package com.xiaofei.framework.context.support;

import com.xiaofei.framework.anno.Autowired;
import com.xiaofei.framework.beans.BeanDefinition;
import com.xiaofei.framework.beans.factory.BeanNameAware;
import com.xiaofei.framework.beans.factory.BeanPostProcessor;
import com.xiaofei.framework.beans.factory.InitializingBean;
import com.xiaofei.framework.beans.factory.support.AnnotationBeanDefinitionReader;
import com.xiaofei.framework.beans.factory.support.BeanDefinitionRegistry;
import com.xiaofei.framework.beans.factory.support.XmlBeanDefinitionReader;

import java.lang.reflect.Field;

/**
 * @Description: Created by IntelliJ IDEA.
 * 注解启动类
 * @Author : 小肥居居头
 * @create 2024/3/17 17:20
 */


public class AnnotationApplicationContext extends AbstractApplicationContext{

    /**
     * 配置类Class字节码(注解独有)
     */
    private Class<?> configClass;

    /**
     * 注解App构造器,创建一个注解Bean读取类,然后调用refresh()方法;
     * 把注解对象导入进来;
     * @param configClass
     */
    public AnnotationApplicationContext(Class configClass) {
        super(new AnnotationBeanDefinitionReader(configClass));
        this.configClass = configClass;
        try {
            this.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String name) throws Exception {
        Object o = this.singletonObjects.get(name);
        if (o != null){
            return o;
        }
        //获取注册表;
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        BeanDefinition beanDefinition = registry.getBeanDefinition(name);
        //注解里面直接有class字节码对象;
        Class<?> clazz = beanDefinition.getClazz();
        Object beanObj = clazz.newInstance();
        //属性注入;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Autowired.class)){
                field.set(beanObj, getBean(field.getName()));
            }
        }
        if (beanObj instanceof BeanNameAware){
            ((BeanNameAware) beanObj).setBeanName(beanDefinition.getId());
        }
        if (beanObj instanceof InitializingBean){
            ((InitializingBean) beanObj).afterPropertiesSet();
        }
        if (beanObj instanceof BeanPostProcessor){
            beanObj = ((BeanPostProcessor) beanObj).postProcessAfterInitialization(beanObj, beanDefinition.getId());
        }

        //此时已经new好了一个对象,但是属性或者依赖还没有 配置好;
        singletonObjects.put(name, beanObj);
        return beanObj;
    }

    @Override
    public <T> T getBean(String name, Class<? extends T> clazz) throws Exception {
        return null;
    }

    /**
     * 将Annotation --> 静态的BeanDefinition;
     * @throws Exception
     */
    @Override
    public void refresh() throws Exception {
        //调用Reader的扫描导入方法;
        beanDefinitionReader.loadBeanDefinitions();
        //初始化bean,会循环调用本类的getBean方法;
        finishBeanInitialization();
    }
}
