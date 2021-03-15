package com.mrlv.Handler.impl;

import com.mrlv.Handler.AbstractHandler;
import com.mrlv.Handler.factory.Factory;
import org.springframework.stereotype.Component;

@Component
public class Test2Handler extends AbstractHandler {

    public void print() {
        System.out.println("我是Test2");
    }

    public void afterPropertiesSet() {
        Factory.register("Test2", this);
    }
}
