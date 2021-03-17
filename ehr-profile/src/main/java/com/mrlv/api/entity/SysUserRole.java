package com.mrlv.api.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户和角色关联实体类
 */
@TableName("sys_user_role")
public class SysUserRole {

    private String userId;

    private String roleId;

    public SysUserRole() {
        super();
    }

    public SysUserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
