package com.xiaofei.framework;

import com.xiaofei.framework.anno.ComponentScan;
import com.xiaofei.framework.context.ApplicationContext;
import com.xiaofei.framework.context.support.AnnotationApplicationContext;
import com.xiaofei.framework.context.support.ClassPathXmlApplicationContext;
import com.xiaofei.framework.xiaofei.fei.fei.XmlClassA;
import com.xiaofei.framework.xiaofei.fei.fei.XmlClassB;

/**
 * @Description: Created by IntelliJ IDEA.
 * @Author : 小肥居居头
 * @create 2024/3/17 17:39
 */

@ComponentScan("com.xiaofei.framework")
public class ApplicationStarter {
    public static void main(String[] args) throws Exception {
        ApplicationContext application = new AnnotationApplicationContext(ApplicationStarter.class);
        //如果Test有代理方法,那么这里的getBean获得的应该是Test的代理对象
        XmlClassA xmlClassA1 = (XmlClassA)application.getBean("xmlClassA");
        XmlClassB xmlClassB1 = (XmlClassB)application.getBean("xmlClassB");
        application.getBean("xmlClassB");
        application.getBean("test");
        Test test = (Test)application.getBean("test");
        test.printHello();
        test.afterPropertiesSet();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        XmlClassB xmlClassB2 = (XmlClassB)applicationContext.getBean("xmlClassB");
        XmlClassA xmlClassA2 = (XmlClassA)applicationContext.getBean("xmlClassA");
        System.out.println(xmlClassB2);

    }
}
