package com.mrlv.Handler.impl;

import com.mrlv.Handler.AbstractHandler;
import com.mrlv.Handler.factory.Factory;
import org.springframework.stereotype.Component;

@Component
public class Test1Handler extends AbstractHandler {


    public void print() {
        System.out.println("我是Test1");
    }


    public void afterPropertiesSet() {
        Factory.register("Test1", this);
    }
}
