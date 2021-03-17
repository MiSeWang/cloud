package com.mrlv.api.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mrlv.api.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与角色的关联类
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 批量插入用户与角色的关联关系
     * @param sysUserRoles
     * @return
     */
    Integer insertBatch(@Param("sysUserRoles") List<SysUserRole> sysUserRoles);
}
