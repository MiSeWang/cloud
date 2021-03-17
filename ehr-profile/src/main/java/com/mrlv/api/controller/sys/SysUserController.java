package com.mrlv.api.controller.sys;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.mrlv.api.constant.DelFlag;
import com.mrlv.api.entity.SysUser;
import com.mrlv.api.service.ISysRoleService;
import com.mrlv.api.service.ISysUserService;
import com.mrlv.api.vo.ResultMsg;
import com.mrlv.api.vo.SysUserListVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 *  用户管理
 */
@RequestMapping("/sysUser")
@RestController
public class SysUserController {

    private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;


    /**
     * 查询用户管理列表数据
     * @return
     */
    @GetMapping("/queryUsers")
    public ResultMsg queryUsers(){
        List<SysUserListVO> sysUserListVOs = sysUserService.queryUsers();
        return ResultMsg.createSuccessDatas("page", sysUserListVOs);
    }

    @PostMapping("/saveUser")
    public ResultMsg saveUser(@RequestBody String body){
        Date date = new Date();
        SysUser sysUser = JSON.parseObject(body, SysUser.class);
        JSONArray roleIds = JSONObject.parseObject(body).getJSONArray("roleArray");
        if (StringUtils.isBlank(sysUser.getId())){
            if (StringUtils.isEmpty(sysUser.getLoginName())) {
                return ResultMsg.createErrorMessage("用户账号不能为空");
            }
            SysUser userDB = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("login_name", sysUser.getLoginName()));
            if (userDB != null) {
                return ResultMsg.createErrorMessage("用户已经注册");
            }
            sysUser.setCreateTime(date);
            return ResultMsg.result(sysUserService.insertUser(sysUser, roleIds.toJavaList(String.class)));
        } else {
            if (StringUtils.isEmpty(sysUser.getPassword())){
                return ResultMsg.createErrorMessage("密码不能为空");
            }
            sysUser.setUpdateTime(date);
            return ResultMsg.result(sysUserService.updateUser(sysUser, roleIds.toJavaList(String.class)));
        }
        //密码加密
//        RandomNumberGenerator saltGen = new SecureRandomNumberGenerator();
//        String salt = saltGen.nextBytes().toBase64();
//        String hashedPwd = new Sha256Hash(user.getPassword(), salt, 1024).toBase64();
        //保存新用户数据
//        user.setPwd(hashedPwd);
//        user.setSalt(salt);
    }

    @PostMapping("/delUser")
    public ResultMsg delUser(@RequestBody String body){
        String oper = "delete user";
        log.info("{}, body: {}", oper, "");
        JSONObject userObject = JSON.parseObject(body);
        SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
        if (userObject.getBoolean("isBatch")){
            JSONArray array = userObject.getJSONArray("id");
            if (array.isEmpty()){
                return ResultMsg.createErrorMessage("无法删除用户：参数为空（用户id）");
            }
            List<SysUser> users =  Lists.newArrayList();
            for (String id : array.toJavaList(String.class)) {
                if (sysUser.getId().equals(id)){
                    return ResultMsg.createErrorMessage("系统限制：不能删除当前登录账号");
                }
                SysUser user = new SysUser();
                user.setId(id);
                user.setDelFlag(DelFlag.AL_DEL.value());
                users.add(user);
            }
            return ResultMsg.result(sysUserService.updateBatchById(users));
        } else {
            String id = userObject.getString("id");
            if (id == null){
                return ResultMsg.createErrorMessage("无法删除用户：参数为空（用户id）");
            }
            if (sysUser.getId().equals(id)){
                return ResultMsg.createErrorMessage("系统限制：不能删除当前登录账号");
            }
            SysUser user = new SysUser();
            user.setId(id);
            user.setDelFlag(DelFlag.AL_DEL.value());
            return ResultMsg.result(sysUserService.updateById(user));
        }
    }


}
