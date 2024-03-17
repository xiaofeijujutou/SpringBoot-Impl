package com.xiaofei.framework.beans.factory.support;

/**
 * @Description: Created by IntelliJ IDEA.
 * 功能:给一个文件路径或者项目路径,读取Bean定义的配置文件,
 * 然后封装成BeanDefinitionRegistry注册列表;
 * 子类需要持有BeanDefinitionRegistry注册列表;
 * @Author : 小肥居居头
 * @create 2024/3/14 18:16
 */


public interface BeanDefinitionReader {
    //获取注册表对象
    BeanDefinitionRegistry getRegistry();

    /**
     * 需要从xml->Definitions或者Annotation->Definitions
     * 具体的保存在子类属性中;
     * @throws Exception
     */
    void loadBeanDefinitions() throws Exception;
}
