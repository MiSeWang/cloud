package com.mrlv.api.controller;

import com.mrlv.api.constant.Codes;
import com.mrlv.api.vo.ResultMsg;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * 统一捕获异常,返给前台一个json信息,前台根据这个json信息显示对应的提示,或者做页面的跳转
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //不满足@RequiresGuest注解时抛出的异常信息
    private static final String GUEST_ONLY = "Attempting to perform a guest-only operation";

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public ResultMsg handleShiroException(ShiroException e, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setHeader("contentType", "text/html; charset=utf-8");
        String name = e.getClass().getSimpleName();
        log.error("shiro执行出错：{}", name);
        return new ResultMsg(Codes.SHIRO_ERR, "鉴权或者授权过程出错", null);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public ResultMsg page401(UnauthenticatedException e, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("contentType", "text/html; charset=utf-8");
        String eMsg = e.getMessage();
        if (StringUtils.startsWithIgnoreCase(eMsg,GUEST_ONLY)){
            return new ResultMsg(Codes.UNAUTHEN, "只允许游客访问，若您已登录，请先退出登录", null)
                    .data("detail",e.getMessage());
        }else{
            return new ResultMsg(Codes.UNAUTHEN, "用户未登录", null)
                    .data("detail",e.getMessage());
        }
    }


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResultMsg page403(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("contentType", "text/html; charset=utf-8");
        return new ResultMsg(Codes.UNAUTHZ, "用户没有访问权限", null
        );
    }


}
