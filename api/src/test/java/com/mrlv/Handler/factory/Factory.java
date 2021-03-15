package com.mrlv.Handler.factory;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.mrlv.Handler.AbstractHandler;

import java.util.Map;

/**
 * 工厂设计模式
 */
public class Factory {

    private static Map<String, AbstractHandler> strategyMap = Maps.newHashMap();

    public static AbstractHandler getInvokeStrategy(String name){
        return strategyMap.get(name);
    }

    public static void register(String str, AbstractHandler handler){
        if (StringUtils.isEmpty(str) || null == handler) return;
        strategyMap.put(str, handler);
    }
}
