package com.mrlv.api.service;

import com.baomidou.mybatisplus.service.IService;
import com.mrlv.api.entity.SysPerm;
import com.mrlv.api.vo.AuthVo;
import com.mrlv.api.vo.PermTreeVo;

import java.util.List;
import java.util.Set;

/**
 * 系统权限
 */
public interface ISysPermService extends IService<SysPerm> {

    /**
     * 根据用户id获取权限
     * @param userId
     * @return
     */
    Set<AuthVo> getPermsByUserId(String userId);

    /**
     * 根据传回type查询对应的权限
     * @param type 1.菜单 2.按钮 3.接口 4.特殊, 可以传多个，用,分割
     * @return
     */
    List<PermTreeVo> getPermTreeVo(String type);


}
