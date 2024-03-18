package com.xiaofei.framework.beans.factory.annotation;

import com.xiaofei.framework.anno.Component;
import com.xiaofei.framework.anno.ComponentScan;
import com.xiaofei.framework.anno.Scope;
import com.xiaofei.framework.beans.factory.support.BeanDefinitionReader;
import com.xiaofei.framework.beans.factory.support.BeanDefinitionRegistry;
import com.xiaofei.framework.beans.factory.support.SimpleBeanDefinitionRegistry;
import com.xiaofei.framework.utils.StringUtils;

import java.beans.Introspector;
import java.io.File;
import java.net.URL;

/**
 * @Description: Created by IntelliJ IDEA.
 * 注解Reader
 * @Author : 小肥居居头
 * @create 2024/3/17 17:45
 */


public class AnnotationBeanDefinitionReader implements BeanDefinitionReader {

    /**
     * 配置类信息;
     */
    private Class configClass;

    /**
     * 全局变量的classLoader;
     */
    private ClassLoader classLoader;
    /**
     * 存储了所有Bean定义的模样,只有String字符串来描述,并没有实际的对象;
     */
    private BeanDefinitionRegistry beanDefinitionRegistry;

    /**
     * 构造器,需要一个配置类信息;
     * @param configClass 配置类信息
     */
    public AnnotationBeanDefinitionReader(Class configClass) {
        //new一个注册器容器;
        this.beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        this.configClass = configClass;
    }

    /**
     * 获取容器
     * @return
     */
    public BeanDefinitionRegistry getRegistry() {
        return beanDefinitionRegistry;
    }

    /**
     * 需要根据配置类的消息,解析所有的Bean,把Bean信息封装成Register;
     * @throws Exception
     */
    public void loadBeanDefinitions() throws Exception {
        if (!configClass.isAnnotationPresent(ComponentScan.class)) {
            throw new Exception("启动类没有扫描注解");
        }
        //获取注解信息;
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        //注解里面的数据,直接调用方法就能拿到,转换成相对路径;
        String relativePath = componentScanAnnotation.value().replace(".", "/");
        this.classLoader = Thread.currentThread().getContextClassLoader();
        //取得绝对路径
        URL absolutePath = classLoader.getResource(relativePath);
        File scannedFile = new File(absolutePath.getFile());
        //递归寻找所有的含Component注解的类
        registerAllBean(scannedFile);

    }

    /**
     * 根据类的文件路径来注册Bean;
     * @param classPath 相对路径;
     */
    private void registerBean(File classPath) {
        String classAbsolutePath = classPath.getAbsolutePath();
        if (classAbsolutePath.endsWith(".class")) {
            //提取出class对象,需要类的全限定名,用于反射
            String classRelativeName = classAbsolutePath.substring(classAbsolutePath.lastIndexOf("com"),
                    classAbsolutePath.indexOf(".class")).replace(File.separator, ".");
            try {
                //使用classLoader将其装载到内存中
                Class<?> clazz = classLoader.loadClass(classRelativeName);
                //如果这个类含有了Component注解
                if (clazz.isAnnotationPresent(Component.class)) {
                    AnnotationBeanDefinition beanDefinition = new AnnotationBeanDefinition();
                    Component component = clazz.getAnnotation(Component.class);
                    String beanName = component.value();
                    //如果没有设置beanName,默认用小驼峰;
                    if ("".equals(beanName)) {
                        beanName = Introspector.decapitalize(clazz.getSimpleName());
                    }
                    //生成BeanDefinition,解析单例或原型
                    String scope = "singleton";
                    if (clazz.isAnnotationPresent(Scope.class)) {
                        scope = StringUtils.isEmpty(clazz.getAnnotation(Scope.class).value()) ?
                                "singleton" : clazz.getAnnotation(Scope.class).value();
                    }
                    beanDefinition.setId(beanName);
                    beanDefinition.setClazz(clazz);
                    beanDefinition.setScope(scope);
                    beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 扫描包下面的所有类,并注册到BeanDefinition中
     * @param scannedFile
     */
    private void registerAllBean(File scannedFile) {
        //如果是文件夹
        if (scannedFile.isDirectory()) {
            File[] files = scannedFile.listFiles();
            if (files != null) {
                //且文件夹非空,遍历文件夹里面的内容
                for (File f : files) {
                    //文件夹里面还是文件夹
                    if (f.isDirectory()) {
                        registerAllBean(f); // 递归调用
                    //文件夹里面是文件
                    } else {
                        registerBean(f);
                    }
                }
            }
        }
    }


}
