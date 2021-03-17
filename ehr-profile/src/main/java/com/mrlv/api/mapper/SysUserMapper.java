package com.mrlv.api.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mrlv.api.entity.SysUser;
import com.mrlv.api.vo.SysUserListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUserListVO> queryUsers();
}
