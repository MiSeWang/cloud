package com.mrlv;

import com.mrlv.Handler.AbstractHandler;
import com.mrlv.Handler.factory.Factory;

public class Main {

    public static void main(String[] args) {

        check("Test1");
        //通过 策略模式 + 模板模式 + 工厂模式 将繁琐的判断换成以下代码
        AbstractHandler test1 = Factory.getInvokeStrategy("Test1");
        AbstractHandler test2 = Factory.getInvokeStrategy("Test2");
        test1.print();
        test2.print();

    }

    static void check(String name){
        if ("Test1".equals(name)){
            System.out.println("???1");
        } else if ("Test2".equals(name)){
            System.out.println("???2");
        }
    }
}
