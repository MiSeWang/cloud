package com.mrlv.api.constant;

import java.util.Objects;

/**
 *
 */
public enum DelFlag {

    /**
     * 有效
     */
    UN_DEL("正常",1),
    /**
     * 无效
     */
    AL_DEL("已删",0);

    private String name;
    private int value;

    DelFlag(String name, int value) {
        this.name = name;
        this.value = value;
    }
    public static DelFlag getDelFlag(int value){
        for (DelFlag delFlag: DelFlag.values()) {
            if (Objects.equals(delFlag.value(), value)) {
                return delFlag;
            }
        }
        return null;
    };

    public int value() {
        return this.value;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
