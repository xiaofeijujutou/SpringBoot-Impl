package com.xiaofei.framework.context.support;


import com.sun.istack.internal.Nullable;
import com.xiaofei.framework.beans.MultiplePropertyValues;
import com.xiaofei.framework.beans.PropertyValue;
import com.xiaofei.framework.beans.factory.support.BeanDefinitionRegistry;
import com.xiaofei.framework.beans.factory.xml.XmlBeanDefinition;
import com.xiaofei.framework.beans.factory.xml.XmlBeanDefinitionReader;
import com.xiaofei.framework.utils.ClassUtils;
import com.xiaofei.framework.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description: Created by IntelliJ IDEA.
 * IOC容器具体的子类,用于加载类路径下的xml格式的配置文件;
 * @Author : 小肥居居头
 * @create 2024/3/14 20:11
 */


public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    /**
     * 配置文件路径(xml独有)
     */
    private String configLocation;

    /**
     * 构造器
     * @param configLocation
     */
    public ClassPathXmlApplicationContext(String configLocation) {
        super(new XmlBeanDefinitionReader(configLocation));
        this.configLocation = configLocation;
        try {
            this.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用Bean读取器的根据文件读取方法,将配置文件全部扫描成BeanDefinition
     * @throws Exception
     */
    @Override
    public void refresh() throws Exception {
        //Reader获取所有的注册信息存储到了自身对象;
        beanDefinitionReader.loadBeanDefinitions();
        //完成所有bean初始化,也就是全部存储到容器中
        finishBeanInitialization();
    }
    /**
     * 根据Bean的名字来获取Bean;
     * 判断容器中是否包含Bean对象,如果包含就返回,不包含就创建;
     * @param name
     * @return
     * @throws Exception
     */
    public Object getBean(String name) throws Exception {
        //应该先去一级缓存找,没找到去二级缓存,找到就返回;
        Object o = getSingleton(name);
        if (o != null){
            return o;
        }
        //获取注册表;
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        //注册表中获取注册信息;
        XmlBeanDefinition beanDefinition = (XmlBeanDefinition)registry.getBeanDefinition(name);
        //获取类名,通过反射获取实例;
        String className = beanDefinition.getClassName();
        Class<?> clazz = Class.forName(className);
        //此时是半成品,半成品就应该丢入二缓;
        Object beanObj = clazz.newInstance();
        earlySingletonObjects.put(name, beanObj);
        //此时已经new好了一个对象,但是属性或者依赖还没有配置好;
        MultiplePropertyValues propertyValues = beanDefinition.getMultiplePropertyValues();
        for (PropertyValue property : propertyValues) {
            String propertyName = property.getName();
            String propertyValue = property.getValue();
            String propertyRef = property.getRef();
            if (!StringUtils.isEmpty(propertyRef)){
                //递归调用创建Bean对象的方法
                Object refObj = getBean(propertyRef);
                //创建好了之后使用反射获取set方法来属性注入
                String setterMethodName = StringUtils.getSetterMethodByFieldName(propertyName);
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    if (setterMethodName.equals(declaredMethod.getName())){
                        declaredMethod.invoke(beanObj, refObj);
                    }
                }
            }
            if (!StringUtils.isEmpty(propertyValue)){
                //使用反射获取set方法来属性注入
                String setterMethodName = StringUtils.getSetterMethodByFieldName(propertyName);
                Field field = clazz.getDeclaredField(propertyName);
                //获取参数类型
                Class fieldType = ClassUtils.getFieldType(field);
                Object methodArg = ClassUtils.castStringByType(propertyValue, fieldType);
                Method method = clazz.getMethod(setterMethodName, fieldType);
                method.invoke(beanObj, methodArg);
            }
        }
        //返回对象之前把创建好的对象放入Bean容器中,然后删除其他缓存残留数据;
        singletonObjects.put(name, beanObj);
        earlySingletonObjects.remove(name);
        return beanObj;
    }




    public <T> T getBean(String name, Class<? extends T> clazz) throws Exception {
        Object bean = getBean(name);
        if (bean == null){
            return null;
        }
        return clazz.cast(bean);
    }


}
