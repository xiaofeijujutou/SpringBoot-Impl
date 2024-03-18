package com.xiaofei.framework.xiaofei.fei.fei;

import com.xiaofei.framework.anno.Autowired;
import com.xiaofei.framework.anno.Component;

/**
 * @Description: Created by IntelliJ IDEA.
 * @Author : 小肥居居头
 * @create 2024/3/17 22:52
 */

@Component
public class XmlClassA {
    private Integer i;
    private String s;
    @Autowired
    private XmlClassB xmlClassB;

    public XmlClassB getXmlClassB() {
        return xmlClassB;
    }

    public void setXmlClassB(XmlClassB xmlClassB) {
        this.xmlClassB = xmlClassB;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "XmlClassA{" +
                "i=" + i +
                ", s='" + s + '\'' +
                '}';
    }
}
