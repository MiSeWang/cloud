package com.mrlv.Handler;


import org.springframework.beans.factory.InitializingBean;

/**
 * 策略设计模式 和 模板设计模式
 */
public abstract class AbstractHandler implements InitializingBean {

    public void print() { throw new UnsupportedOperationException(); }

    public void oper(String name) { throw new UnsupportedOperationException(); }
}
