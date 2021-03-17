package com.mrlv.api.vo;

import java.util.HashMap;

public class ResultMsg extends HashMap<String, Object> {
    /////////////////////// 默认的键 ///////////////////////
    public static final String KEY_CODE = "code";
    public static final String KEY_MSG = "msg";
    public static final String KEY_DATA = "data";

    /////////////////////// 默认的值 ///////////////////////
    public static final int DEFAULT_SUCC_CODE = 200;
    public static final int DEFAULT_FAIL_CODE = 500;
    public static final String DEFAULT_SUCC_MSG = "ok";
    public static final String DEFAULT_FAIL_MSG = "fail";

    /////////////////////// 最通用的两个构造函数 ///////////////////////

    /**
     * 无参构造：操作成功返回的响应信息
     */
    public ResultMsg() {
        this.put(KEY_CODE, DEFAULT_SUCC_CODE);
        this.put(KEY_MSG, DEFAULT_SUCC_MSG);
    }

    /**
     * 全参构造
     *
     * @param code
     * @param msg
     * @param data
     */
    public ResultMsg(int code, String msg, Object data) {
        this.put(KEY_CODE, code);
        this.put(KEY_MSG, msg);
        if (data != null) {
            this.put(KEY_DATA, data);
        }
    }

    /////////////////////// 各种简便的静态工厂方法 ///////////////////////
    ////// 操作成功的：
    public static ResultMsg createSuccess() {
        return new ResultMsg();
    }


    public static ResultMsg createSuccessMessage(String message) {
        return new ResultMsg(DEFAULT_SUCC_CODE, message, null);
    }

    public static ResultMsg createSuccessData(Object data) {
        return new ResultMsg(DEFAULT_SUCC_CODE, DEFAULT_SUCC_MSG, data);
    }

    public static ResultMsg createSuccessDatas(String dataKey, Object dataVal) {
        return new ResultMsg(DEFAULT_SUCC_CODE, DEFAULT_SUCC_MSG, null)
                .data(dataKey, dataVal);
    }

    ////// 操作失败的：
    public static ResultMsg createError() {
        return new ResultMsg(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MSG, null);
    }

    public static ResultMsg createErrorMessage(String message) {
        return new ResultMsg(DEFAULT_FAIL_CODE, message, null);
    }

    public static ResultMsg createErrorData(Object data) {
        return new ResultMsg(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MSG, data);
    }

    public static ResultMsg createErrorDatas(String dataKey, Object dataVal) {
        return new ResultMsg(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MSG, null)
                .data(dataKey, dataVal);
    }

    ////// 操作动态判定成功或失败的：
    public static ResultMsg result(boolean success) {
        return new ResultMsg(
                (success ? DEFAULT_SUCC_CODE : DEFAULT_FAIL_CODE),
                (success ? DEFAULT_SUCC_MSG : DEFAULT_FAIL_MSG),
                null);
    }

    public static ResultMsg result(boolean success, Object data) {
        return new ResultMsg(
                (success ? DEFAULT_SUCC_CODE : DEFAULT_FAIL_CODE),
                (success ? DEFAULT_SUCC_MSG : DEFAULT_FAIL_MSG),
                data);
    }

    public static ResultMsg result(boolean success, String dataKey, Object dataVal) {
        return new ResultMsg(
                (success ? DEFAULT_SUCC_CODE : DEFAULT_FAIL_CODE),
                (success ? DEFAULT_SUCC_MSG : DEFAULT_FAIL_MSG),
                null)
                .data(dataKey, dataVal);
    }

    /////////////////////// 各种链式调用方法 ///////////////////////

    /**
     * 设置操作结果的代码
     */
    public ResultMsg code(int code) {
        this.put(KEY_CODE, code);
        return this;
    }

    /**
     * 设置操作结果的信息
     */
    public ResultMsg msg(String message) {
        this.put(KEY_MSG, message);
        return this;
    }

    /**
     * 设置操作返回的数据
     */
    public ResultMsg data(Object dataVal) {
        this.put(KEY_DATA, dataVal);
        return this;
    }

    /**
     * 设置操作返回的数据，数据使用自定义的key存储
     */
    public ResultMsg data(String dataKey, Object dataVal) {
        this.put(dataKey, dataVal);
        return this;
    }
}
