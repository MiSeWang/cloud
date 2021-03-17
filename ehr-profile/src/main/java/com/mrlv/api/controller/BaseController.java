package com.mrlv.api.controller;

import com.mrlv.api.entity.SysUser;
import org.apache.shiro.SecurityUtils;

public class BaseController {

    protected SysUser user;

    public BaseController(){
        super();
        user = (SysUser) SecurityUtils.getSubject().getPrincipal();
    }
}
