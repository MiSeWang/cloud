package com.mrlv.api.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mrlv.api.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> getRolesByUserId(@Param("userId") String userId);
}
