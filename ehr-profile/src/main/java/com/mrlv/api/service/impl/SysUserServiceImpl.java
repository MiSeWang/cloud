package com.mrlv.api.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mrlv.api.mapper.SysUserMapper;
import com.mrlv.api.mapper.SysUserRoleMapper;
import com.mrlv.api.entity.SysUser;
import com.mrlv.api.entity.SysUserRole;
import com.mrlv.api.service.ISysUserService;
import com.mrlv.api.vo.SysUserListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserListVO> queryUsers(){
        return baseMapper.queryUsers();
    }

    @Transactional
    @Override
    public Boolean insertUser(SysUser sysUser, List<String> roleIds) {
        Integer insert = baseMapper.insert(sysUser);
        if (insert > 0){
            List<SysUserRole> sysUserRoles = roleIds.stream().map((e) -> new SysUserRole(sysUser.getId(), e)).collect(Collectors.toList());
            if (sysUserRoles != null && sysUserRoles.size() > 0){
                sysUserRoleMapper.insertBatch(sysUserRoles);
            }
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean updateUser(SysUser sysUser, List<String> roleIds) {
        Integer integer = baseMapper.updateById(sysUser);
        if (integer > 0){
            sysUserRoleMapper.delete(new EntityWrapper<SysUserRole>().eq("user_id", sysUser.getId()));
            List<SysUserRole> sysUserRoles = roleIds.stream().map((e) -> new SysUserRole(sysUser.getId(), e)).collect(Collectors.toList());
            if (sysUserRoles != null && sysUserRoles.size() > 0){
                sysUserRoleMapper.insertBatch(sysUserRoles);
            }
            return true;
        }
        return false;
    }
}
