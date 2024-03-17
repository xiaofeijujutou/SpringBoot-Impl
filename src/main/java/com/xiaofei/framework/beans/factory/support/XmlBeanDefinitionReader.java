package com.xiaofei.framework.beans.factory.support;

import com.xiaofei.framework.beans.BeanDefinition;
import com.xiaofei.framework.beans.MultiplePropertyValues;
import com.xiaofei.framework.beans.PropertyValue;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @Description: Created by IntelliJ IDEA.
 * @Author : 小肥居居头
 * @create 2024/3/14 18:18
 */


public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private String configLocation;

    /**
     * 存储了所有Bean定义的模样,只有String字符串来描述,并没有实际的对象;
     */
    private BeanDefinitionRegistry beanDefinitionRegistry;

    /**
     * 构造器,new一个Reader之前需要初始化注册器,也就是保存BeanDefinition的地方;
     * @param configLocation
     */
    public XmlBeanDefinitionReader(String configLocation) {
        //new一个注册器容器;
        this.beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        this.configLocation = configLocation;
    }

    /**
     * 获取BeanDefinition容器;
     * @return
     */
    public BeanDefinitionRegistry getRegistry() {
        return beanDefinitionRegistry;
    }

    /**
     * 根据xml的地址,解析xml读取bean,然后封装到BeanDefinition中去;
     * configLocation 类路径的配置文件;
     * @throws Exception
     */
    public void loadBeanDefinitions() throws Exception {
        //dom4j的xml解析器;
        SAXReader reader = new SAXReader();
        //使用类加载器的getResourceAsStream方法把xml文件读取到内存中,并且转换成inputStream;
        InputStream inputStream = XmlBeanDefinitionReader.class.getClassLoader().getResourceAsStream(this.configLocation);
        //xml解析器.读取(输入流),返回一个Document,也就是整个xml文件;
        Document document = reader.read(inputStream);
        //从xml文件获取"bean"标签;
        Element rootElement = document.getRootElement();
        List<Element> beans = rootElement.elements("bean");
        for (Element beanElement : beans) {
            //封装<bean>标签的基础属性成一个BeanDefinition;
            BeanDefinition beanDefinition = new BeanDefinition();
            //设置<bean>标签基础属性:
            String id = beanElement.attributeValue("id");
            String className = beanElement.attributeValue("class");
            beanDefinition.setId(id);
            beanDefinition.setClassName(className);
            //声明<bean>标签子标签的<property>:
            MultiplePropertyValues multiplePropertyValues = new MultiplePropertyValues();
            beanDefinition.setMultiplePropertyValues(multiplePropertyValues);
            //封装<bean>标签下所有子标签<property>的基础属性;
            List<Element> propertyElements = beanElement.elements("property");
            for (Element beanProperty : propertyElements) {
                String  name = beanProperty.attributeValue("name");
                String  ref = beanProperty.attributeValue("ref");
                String  value = beanProperty.attributeValue("value");
                PropertyValue propertyValue = new PropertyValue(name, ref, value);
                multiplePropertyValues.addPropertyValue(propertyValue);
            }
            //封装完毕,注册到注册中心
            beanDefinitionRegistry.registerBeanDefinition(id, beanDefinition);
        }
    }
}
