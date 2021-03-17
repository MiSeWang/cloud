package com.mrlv.api.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 角色和权限关联实体类
 */
@TableName("sys_role_perm")
public class SysRolePerm {

    private String roleId;

    private String permId;

    public SysRolePerm(String roleId, String permId) {
        this.roleId = roleId;
        this.permId = permId;
    }

    public SysRolePerm() {
        super();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermId() {
        return permId;
    }

    public void setPermId(String permId) {
        this.permId = permId;
    }

}
