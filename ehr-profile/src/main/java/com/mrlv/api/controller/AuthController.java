package com.mrlv.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mrlv.api.constant.Codes;
import com.mrlv.api.entity.SysUser;
import com.mrlv.api.vo.ResultMsg;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;

/**
 * 用户登录服务
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    /**
     * 登录接口，由于UserService中是模拟返回用户信息的，
     * 所以用户名随意，密码123456
     *
     * @param body
     * @return
     */
    @PostMapping("/login")
    public ResultMsg login(@RequestBody String body){
        String oper = "user login";
        log.info("{}, body: {}", oper, body);
        //将获取的报文转为Object
        JSONObject userObject = JSON.parseObject(body);
        String loginName = userObject.getString("loginName");
        String password = userObject.getString("password");
        if (StringUtils.isEmpty(loginName)){
            return ResultMsg.createErrorMessage("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            return ResultMsg.createErrorMessage("密码不能为空");
        }
        Subject currentUser = SecurityUtils.getSubject();
        try{
            //登陆
            currentUser.login(new UsernamePasswordToken(loginName, password));
            //从session中取出用户信息
            SysUser user = (SysUser)currentUser.getPrincipal();
            if (user == null) throw new AuthenticationException();
            log.info("user login: {}, sessionId: {}",user.getName(),currentUser.getSession().getId());
            //返回登录用户的信息给前台，含用户的所有角色和权限
//            Set<AuthVo> roles = new HashSet<>();    //用户所有角色值，用于shiro做角色权限的判断
//            Set<AuthVo> perms = new HashSet<>();    //用户所有权限值，用于shiro做资源权限的判断
//            perms.add(new AuthVo("所有权限", "*"));
//            roles.add(new AuthVo("测试管理员", "root"));
            return ResultMsg.createSuccess()
                    .data("token", UUID.randomUUID().toString())
                    .data("uid",user.getId())
                    .data("nick",user.getName())
                    .data("roles",user.getRoles())
                    .data("perms",user.getPerms());
        } catch (UnknownAccountException uae) {
            log.warn("用户帐号不正确");
            return ResultMsg.createErrorMessage("用户帐号或密码不正确");
        } catch (IncorrectCredentialsException ice) {
            log.warn("用户密码不正确");
            return ResultMsg.createErrorMessage("用户帐号或密码不正确");
        } catch (LockedAccountException lae) {
            log.warn("用户帐号被锁定");
            return ResultMsg.createErrorMessage("用户帐号被锁定不可用");
        } catch (AuthenticationException ae) {
            log.warn("登录出错");
            return ResultMsg.createErrorMessage("登录失败："+ae.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResultMsg logout(){
        String oper = "user logout";
        log.info("{}", oper);
        SecurityUtils.getSubject().logout();
        return new ResultMsg();
    }

    @GetMapping("/info")
    public ResultMsg info(){
        String oper = "get user info";
        Subject subject = SecurityUtils.getSubject();
        Serializable sessionId = subject.getSession().getId();
        log.info("{},sessionId:{}", oper, sessionId);
        //从session取出用户信息
        SysUser sysUser = (SysUser)subject.getPrincipal();
        if (sysUser == null){
            //告知前台，登陆session失效
            return new ResultMsg(Codes.SESSION_TIMEOUT, "登陆已经失效", null);
        } else {
//            Set<AuthVo> roles = new HashSet<>();    //用户所有角色值，用于shiro做角色权限的判断
//            Set<AuthVo> perms = new HashSet<>();    //用户所有权限值，用于shiro做资源权限的判断
//            perms.add(new AuthVo("所有权限", "*"));
//            roles.add(new AuthVo("测试管理员", "root"));
            //返回登陆用户的信息给前台，含用户的所有角色和权限
            return  ResultMsg.createSuccess()
                    .data("loginName",sysUser.getLoginName())
                    .data("name",sysUser.getName())
                    .data("photo","")
                    .data("roles", sysUser.getRoles())
                    .data("perms", sysUser.getPerms());
        }
    }

    /**
     * shiro.loginUrl映射到这里，我在这里直接抛出异常交给GlobalExceptionHandler来统一返回json信息，
     * 您也可以在这里直接返回json，不过这样子就跟GlobalExceptionHandler中返回的json重复了。
     * @return
     */
    @RequestMapping("/page/401")
    public ResultMsg page401() {
        throw new UnauthenticatedException();
    }

    /**
     * shiro.unauthorizedUrl映射到这里。由于约定了url方式只做鉴权控制，不做权限访问控制，
     * 也就是说在ShiroConfig中如果没有做roles[js],perms[mvn:install]这样的权限访问控制配置的话，是不会跳转到这里的。
     * @return
     */
    @RequestMapping("/page/403")
    public ResultMsg page403() {
        throw new UnauthorizedException();
    }

    /**
     * 登录成功跳转到这里，直接返回json。但是实际情况是在login方法中登录成功后返回json了。
     * @return
     */
    @RequestMapping("/page/index")
    public ResultMsg pageIndex() {
        return new ResultMsg(1,"index page",null);
    }
}
