package com.mrlv.api.constant;

public enum SocketCode {

    /**
     *  事件消息
     */
    EVENT_MESSAGE(2000),
    /**
     * 更新其他容器的页面（用于解决ios多容器的问题）
     */
    EVENT_MESSAGE_REFRESH(2001),
    /**
     * 获取推送公告信息
     */
    PUSH_MESSAGE(2002),
    /**
     * 刷新指定的ios容器
     */
    REFRESH_PAGE(2003),
    /**
     * 刷新查询任务
     */
    PUSH_TASK_MESSAGE(2004),
    /**
     * 数据执行中处理异常
     */
    EXECUTE_ERROR(5000),
    /**
     * 连接socket失败，非法连接
     */
    INIT_ERROR(5005),
    /**
     * 心跳测试
     */
    HEARTBEAT(6000);

    private Integer code;

    SocketCode(Integer code){
        this.code = code;
    }

    public static SocketCode getSocketCode(Integer code){
        SocketCode[] values = SocketCode.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].code.equals(code)){
                return values[i];
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

}
