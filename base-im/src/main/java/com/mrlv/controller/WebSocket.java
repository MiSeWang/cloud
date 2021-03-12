package com.mrlv.controller;


import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by CW6477 on 2019/7/11.
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/webSocket/{param}")
@Component
public class WebSocket {

    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

    //工号
    private String loginName = "";
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
//        try {
//            JSONObject request = JSONObject.parseObject(message);
//            SocketCode code = SocketCode.getSocketCode(request.getInteger("code"));
//            switch (code) {
//                case EVENT_MESSAGE:
//                    JSONObject data = JSONObject.parseObject(request.getString("data"));
//                    IEventMessageService eventMessageService = SpringUtils.getBean("eventMessageService", IEventMessageService.class);
//                    List<String> loginNames = eventMessageService.noticeEventMessage(data.getString("eventId"), this.loginName);
//                    sendInfos(SocketSendUtils.sendJson(SocketCode.EVENT_MESSAGE, data.getString("eventId")), loginNames);
//                    logger.info(this.loginName + "-来自客户端的消息:" + message);
//                    break;
//                case EVENT_MESSAGE_REFRESH:  //通知,自己的其他容器刷新
//                    JSONObject obj = JSONObject.parseObject(request.getString("data"));
//                    if (obj.getBoolean("operate") != null && obj.getBoolean("operate")){
//                        String[] array = {"todo-list", "contact", "project", "me"}; //要刷新的容器
//                        for (int i = 0; i < array.length; i++) {
//                            sendInfo(SocketSocketSendUtils.sendJson(SocketCode.REFRESH_PAGE,array[i]), this.loginName);
//                        }
//                    }
//                    break;
//                case HEARTBEAT:
//                    sendInfo(SocketSendUtils.sendJson(SocketCode.HEARTBEAT, "60s一次的检查"), this.loginName);
//                    break;
//            }
//        } catch (IOException e) {
//            session.getBasicRemote().sendText(SocketSendUtils.sendJson(SocketCode.EXECUTE_ERROR, "数据执行处理错误"));
            logger.error("数据执行处理错误!{}");
//        }
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 点对点发送消息 message：
     * */
    public static void sendInfo(String message, String loginName) throws IOException {
        for (WebSocket eventMessageWs : webSocketSet) {
            if (loginName.equals(eventMessageWs.loginName)){
                eventMessageWs.sendMessage(message);
            }
        }
    }
    /**
     * 点对点群发送消息 message：
     * */
    public static void sendInfos(String message, List<String> loginNames) throws IOException {
        for (WebSocket eventMessageWs : webSocketSet) {
            if (loginNames.contains(eventMessageWs.loginName)){
                eventMessageWs.sendMessage(message);
            }
        }
    }

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value="param") String loginName, Session session) {
//        UserInfoMapper userInfoMapper = SpringUtils.getBean("userInfoMapper", UserInfoMapper.class);
//        List<UserInfo> userInfos = userInfoMapper.selectList(new EntityWrapper<UserInfo>().eq("login_name", loginName).eq("status", 1));
//        if (userInfos != null && userInfos.size()>0) {
            this.loginName = loginName;
            this.session = session;
            webSocketSet.add(this); //添加到set中
            addOnlineCount();
            logger.info("有新窗口开始监听:" + loginName + ",当前在线连接数为" + getOnlineCount());
//        } else {
//            logger.info("非法连接！" + loginName);
//            try {
//                session.getBasicRemote().sendText(SocketSendUtils.sendJson(SocketCode.INIT_ERROR, "连接无法通过验证."));
//                session.close();
//            } catch (IOException e) {
//                logger.info("关闭非法连接失败: {}", ExceptionUtil.getMessage(e));
//                this.onClose();
//            }
//        }
    }
    /**
     * socket连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        boolean remove = webSocketSet.remove(this);//
        if (remove) {
            subOnlineCount();           //在线数减1
            logger.info(this.loginName + "断开连接！当前在线人数为" + getOnlineCount());
        } else {
            logger.info("eventMessage非法连接关闭");
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        //下联你
//        logger.error("发生错误!{}", ExceptionUtil.getMessage(e));
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }
}
