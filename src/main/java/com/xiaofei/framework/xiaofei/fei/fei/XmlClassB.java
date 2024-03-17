package com.xiaofei.framework.xiaofei.fei.fei;

/**
 * @Description: Created by IntelliJ IDEA.
 * @Author : 小肥居居头
 * @create 2024/3/17 22:52
 */


public class XmlClassB {
    private Integer i;
    private String s;
    private XmlClassA xmlClassA;

    @Override
    public String toString() {
        return "XmlClassB{" +
                "i=" + i +
                ", s='" + s + '\'' +
                ", xmlClassA=" + xmlClassA +
                '}';
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

    public XmlClassA getXmlClassA() {
        return xmlClassA;
    }

    public void setXmlClassA(XmlClassA xmlClassA) {
        this.xmlClassA = xmlClassA;
    }
}
