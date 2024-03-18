package com.xiaofei.framework.beans.factory.xml;

import com.xiaofei.framework.beans.MultiplePropertyValues;
import com.xiaofei.framework.beans.PropertyValue;
import com.xiaofei.framework.beans.factory.BeanPostProcessor;
import com.xiaofei.framework.beans.factory.config.BeanDefinition;
import com.xiaofei.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Created by IntelliJ IDEA.
 * xml定义bean的pojo
 * @Author : 小肥居居头
 * @create 2024/3/18 12:39
 */


public class XmlBeanDefinition implements BeanDefinition {
    //beanName
    private String id;
    //class相对路径
    private String className;
    //属性
    private MultiplePropertyValues multiplePropertyValues;
    //范围
    private String scope;
    //类加载器
    private ClassLoader beanClassLoader;

    public XmlBeanDefinition() {
        multiplePropertyValues = new MultiplePropertyValues();
        this.beanClassLoader = XmlBeanDefinition.class.getClassLoader();
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
        PropertyValue[] propertyValues = multiplePropertyValues.getPropertyValues();
        List<String> dependentBeans = new ArrayList<>();
        for (PropertyValue propertyValue : propertyValues) {
            if (!StringUtils.isEmpty(propertyValue.getRef())){
                dependentBeans.add(propertyValue.getRef());
            }
        }
        return dependentBeans.toArray(new String[0]);
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

    public MultiplePropertyValues getMultiplePropertyValues() {
        return multiplePropertyValues;
    }

    public void setMultiplePropertyValues(MultiplePropertyValues multiplePropertyValues) {
        this.multiplePropertyValues = multiplePropertyValues;
    }
}
