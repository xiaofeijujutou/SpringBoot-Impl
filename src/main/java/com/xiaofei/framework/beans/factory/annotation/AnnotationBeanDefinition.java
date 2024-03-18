package com.xiaofei.framework.beans.factory.annotation;

import com.xiaofei.framework.beans.MultiplePropertyValues;
import com.xiaofei.framework.beans.PropertyValue;
import com.xiaofei.framework.beans.factory.BeanPostProcessor;
import com.xiaofei.framework.beans.factory.config.BeanDefinition;
import com.xiaofei.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Created by IntelliJ IDEA.
 * Annotation定义bean的pojo
 * @Author : 小肥居居头
 * @create 2024/3/18 12:39
 */


public class AnnotationBeanDefinition implements BeanDefinition {
    //beanName
    private String id;
    //class相对路径
    private String className;
    //范围
    private String scope;
    //类加载器
    private ClassLoader beanClassLoader;
    //字节码
    private Class<?> clazz;

    public AnnotationBeanDefinition() {
        this.beanClassLoader = AnnotationBeanDefinition.class.getClassLoader();
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return null;
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.className = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return className;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        return;
    }

    /**
     * 以前迁移过来的老方法;
     * @return
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }



}
