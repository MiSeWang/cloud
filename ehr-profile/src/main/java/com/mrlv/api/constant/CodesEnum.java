package com.mrlv.api.constant;

public enum CodesEnum {

    UNAUTHEN(4401), UNAUTHZ(4403), SESSION_TIMEOUT(4433), SHIRO_ERR(4444), SERVER_ERR(5500);

    private int code;

    CodesEnum(int code){
        this.code = code;
    }

    @Override
    public String toString() {
        return code+"";
    }
}
