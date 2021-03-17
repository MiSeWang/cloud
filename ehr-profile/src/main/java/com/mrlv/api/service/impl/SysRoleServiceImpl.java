package com.mrlv.api.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mrlv.api.mapper.SysRoleMapper;
import com.mrlv.api.mapper.SysRolePermMapper;
import com.mrlv.api.entity.SysRole;
import com.mrlv.api.entity.SysRolePerm;
import com.mrlv.api.service.ISysRoleService;
import com.mrlv.api.vo.AuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRolePermMapper sysRolePermMapper;

    @Override
    public Set<AuthVo> getRolesByUserId(String userId) {
        List<SysRole> list = baseMapper.getRolesByUserId(userId);
        Set<AuthVo> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            set.add(new AuthVo(list.get(i).getName(), list.get(i).getName()));
        }
        return set;
    }


    @Override
    public List<String> getPermIdsByRoleId(String roleId){
        List<String> permIds = sysRolePermMapper.selectList(new EntityWrapper<SysRolePerm>().eq("role_id", roleId))
                .stream().map(SysRolePerm::getPermId).collect(Collectors.toList());
        return permIds;
    }

    @Transactional
    @Override
    public void saveRole(SysRole sysRole, List<String> perms) {
        Date date = new Date();
        sysRole.setCreateDate(date);
        baseMapper.insert(sysRole);
        if (perms != null && perms.size() != 0){
            List<SysRolePerm> sysRolePerms = perms.stream().map((e) -> new SysRolePerm(sysRole.getId(), e)).collect(Collectors.toList());
            if (sysRolePerms != null && sysRolePerms.size() > 0) {
                sysRolePermMapper.insertBatch(sysRolePerms);
            }
        }
    }

    @Transactional
    @Override
    public void updateRolePerms(SysRole sysRole, List<String> permsId) {
        baseMapper.updateById(sysRole);
        sysRolePermMapper.delete(new EntityWrapper<SysRolePerm>().eq("role_id", sysRole.getId()));
        List<SysRolePerm> sysRolePerms = permsId.stream().map((e) -> new SysRolePerm(sysRole.getId(), e)).collect(Collectors.toList());
        if (sysRolePerms != null && sysRolePerms.size() > 0) {
            sysRolePermMapper.insertBatch(sysRolePerms);
        }
    }

}
