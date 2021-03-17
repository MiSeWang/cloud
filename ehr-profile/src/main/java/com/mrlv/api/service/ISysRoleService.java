package com.mrlv.api.service;

import com.baomidou.mybatisplus.service.IService;
import com.mrlv.api.entity.SysRole;
import com.mrlv.api.entity.SysUser;
import com.mrlv.api.vo.AuthVo;

import java.util.List;
import java.util.Set;

/**
 * 角色Service
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据用户名获取角色
     * @param userId
     * @return
     */
    Set<AuthVo> getRolesByUserId(String userId);

    /**
     * 保存一个角色以及与权限的关系
     * @param sysRole
     * @param perms
     * @return
     */
    void saveRole(SysRole sysRole, List<String> perms);


    /**
     * 根据角色id获取所有与该角色关联的权限id
     * @param roleId
     * @return
     */
    List<String> getPermIdsByRoleId(String roleId);

    /**
     * 更新角色
     * @param sysRole
     * @param permsId
     */
    void updateRolePerms(SysRole sysRole, List<String> permsId);
}
