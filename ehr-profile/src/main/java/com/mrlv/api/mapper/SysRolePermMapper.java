package com.mrlv.api.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mrlv.api.entity.SysPerm;
import com.mrlv.api.entity.SysRolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与权限的关联类
 */
@Mapper
public interface SysRolePermMapper extends BaseMapper<SysRolePerm> {

    /**
     * 批量插入角色与权限的关联关系
     * @param sysRolePerms
     * @return
     */
    Integer insertBatch(@Param("sysRolePerms") List<SysRolePerm> sysRolePerms);

}
