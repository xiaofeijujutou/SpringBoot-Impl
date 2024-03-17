package com.xiaofei.framework.beans.factory;

/**
 * @Description: Created by IntelliJ IDEA.
 * @Author : 小肥居居头
 * @create 2024/3/17 21:32
 */


public interface InitializingBean {
    /**
     * 在bean属性注入完之后调用,回调;
     */
    void afterPropertiesSet();
}
