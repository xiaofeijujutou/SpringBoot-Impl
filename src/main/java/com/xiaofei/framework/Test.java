package com.xiaofei.framework;

import com.xiaofei.framework.anno.Autowired;
import com.xiaofei.framework.anno.Component;
import com.xiaofei.framework.anno.Scope;
import com.xiaofei.framework.beans.factory.BeanNameAware;
import com.xiaofei.framework.beans.factory.BeanPostProcessor;
import com.xiaofei.framework.beans.factory.InitializingBean;
import com.xiaofei.framework.xiaofei.fei.fei.ITest;
import com.xiaofei.framework.xiaofei.fei.fei.T2;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description: Created by IntelliJ IDEA.
 * @Author : 小肥居居头
 * @create 2024/3/17 19:40
 */

@Component
@Scope
public class Test implements BeanNameAware, InitializingBean, BeanPostProcessor, ITest, MethodInterceptor {

    @Autowired
    private T2 t2;
    private String beanName;
    private String count;
    private String password;

    /**
     * 假设现在要对这个类的toString方法做aop增强;
     * @return
     */
    @Override
    public String toString() {
        System.out.println(t2);
        return "Test....";
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println(this.beanName);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("bean: Test "  + " 参数设置完成");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        //Object jdk = jdk((Test) bean);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        //设置回调函数;
        enhancer.setCallback(this);
        //创建代理对象;
        //这里创建的对象就是原厂TrainStation类的子类对象;
        Test proxyObject = (Test) enhancer.create();
        return proxyObject;


    }

    private Object jdk(Test bean) {
        Test t = bean;
        t.count = "小肥头";
        t.password = "123456";
        //返回的proxyInstance是extends Proxy implement Interfaces,与这个Test对象本体无关;
        Object proxyInstance = Proxy.newProxyInstance(
                t.getClass().getClassLoader(),
                t.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("jdk动态代理所有方法before");
                        Object invoke = method.invoke(t, args);
                        System.out.println("jdk动态代理所有方法after");
                        return invoke;
                    }
                });
        return proxyInstance;
    }

    @Override
    public void printHello() {
        System.out.println("printHello");
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib动态代理所有方法before");
        Object invoke = method.invoke(this, objects);
        System.out.println("cglib动态代理所有方法after");
        return invoke;
    }
}
