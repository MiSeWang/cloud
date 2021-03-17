package com.mrlv.api.service;

import com.baomidou.mybatisplus.service.IService;
import com.mrlv.api.entity.SysUser;
import com.mrlv.api.vo.SysUserListVO;

import java.util.List;

public interface ISysUserService extends IService<SysUser> {

    /**
     * 查询用户管理列表数据
     * @return
     */
    List<SysUserListVO> queryUsers();

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    Boolean insertUser(SysUser sysUser, List<String> roleIds);

    /**
     * 修改用户信息
     * @param sysUser
     * @return
     */
    Boolean updateUser(SysUser sysUser, List<String> roleIds);
}
