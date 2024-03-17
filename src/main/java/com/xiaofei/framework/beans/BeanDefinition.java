package com.xiaofei.framework.beans;

/**
 * @Description: Created by IntelliJ IDEA.
 * Bean的所有信息;
 * id, class, property;
 * @Author : 小肥居居头
 * @create 2024/3/14 17:34
 */


public class BeanDefinition {
    //xml
    //value
    private String id;
    //class相对路径
    private String className;
    //属性
    private MultiplePropertyValues multiplePropertyValues;
    //注解;
    //单例多例
    private String scope;
    //字节码
    private Class<?> clazz;



    public BeanDefinition() {
        multiplePropertyValues = new MultiplePropertyValues();
    }

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

    public String getScope() {
        return scope;
    }
    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setMultiplePropertyValues(MultiplePropertyValues multiplePropertyValues) {
        this.multiplePropertyValues = multiplePropertyValues;
    }
}
