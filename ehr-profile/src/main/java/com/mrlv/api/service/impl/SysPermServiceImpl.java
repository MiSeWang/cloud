package com.mrlv.api.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mrlv.api.mapper.SysPermMapper;
import com.mrlv.api.entity.SysPerm;
import com.mrlv.api.service.ISysPermService;
import com.mrlv.api.vo.AuthVo;
import com.mrlv.api.vo.PermTreeVo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("sysPermService")
public class SysPermServiceImpl extends ServiceImpl<SysPermMapper, SysPerm> implements ISysPermService {

    @Override
    public Set<AuthVo> getPermsByUserId(String userId) {
        List<SysPerm> list = baseMapper.getPermsByUserId(userId);
        return list.stream().map(p -> new AuthVo(p.getName(), p.getVal())).collect(Collectors.toSet());
    }

    @Override
    public List<PermTreeVo> getPermTreeVo(String type) {
        String[] types = type.split(",");

        List<SysPerm> sysPerms = baseMapper.selectList(new EntityWrapper<SysPerm>().in("type", types));

        List<PermTreeVo> permTreeVos = new ArrayList<>();
        sysPerms.stream().forEach((e) ->{
            if (null == e.getParentVal()){
                permTreeVos.add(getPermTreeVo(e, sysPerms, null));
            }
        });
        return permTreeVos;
    }

    //处理树数据
    private static PermTreeVo getPermTreeVo(SysPerm sysPerm, List<SysPerm> sysPerms, String parentVal){
        PermTreeVo permTreeVo = new PermTreeVo(sysPerm);
        if (sysPerm.getLeaf() != 1){    //如果不是叶子节点
            sysPerms.stream().forEach((e)->{
                if (sysPerm.getVal().equals(e.getParentVal())) {
                    permTreeVo.getChildren().add(getPermTreeVo(e, sysPerms, e.getVal()));
                }
            });
        }
        return permTreeVo;
    }
}
