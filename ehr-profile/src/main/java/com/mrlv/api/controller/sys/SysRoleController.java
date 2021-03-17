package com.mrlv.api.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mrlv.api.constant.DelFlag;
import com.mrlv.api.entity.SysRole;
import com.mrlv.api.service.ISysRoleService;
import com.mrlv.api.vo.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    private final static Logger log = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 查询所有角色
     * @return
     */
    @GetMapping("/query")
    public ResultMsg query(){
        String oper = "query role";
        try {
            log.info("{}, body: {}", oper, "");
            List<SysRole> sysRoles = sysRoleService.selectList(new EntityWrapper<SysRole>().eq("del_flag", 1));
            return ResultMsg.createSuccessDatas("page", sysRoles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMsg.createErrorMessage("系统异常，请稍后重试");
        }
    }

    /**
     * 保存一个角色
     * @param body
     * @return
     */
    @PostMapping("/save")
    public ResultMsg save(@RequestBody String body){
        try {
            JSONObject jsonObject = JSONObject.parseObject(body);
            List<String> perms = jsonObject.getJSONArray("perms").toJavaList(String.class);
            SysRole sysRole = new SysRole();
            sysRole.setName(jsonObject.getString("name"));
            sysRole.setRemarks(jsonObject.getString("remarks"));
            sysRole.setStatus(jsonObject.getInteger("useable"));
            sysRoleService.saveRole(sysRole, perms);
            return ResultMsg.createSuccessMessage("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultMsg.createErrorMessage("系统异常，请稍后重试");
    }

    /**
     * 根据角色id获取所有与该角色关联的权限id
     * @param roleId
     * @return
     */
    @GetMapping("/queryPermIdsByRoleId")
    public ResultMsg queryById(String roleId){
        String oper = "queryById role";
        try {
            log.info("{}, body: {}", oper, roleId);
            List<String> permIds = sysRoleService.getPermIdsByRoleId(roleId);
            return ResultMsg.createSuccessData(permIds);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMsg.createErrorMessage("系统异常，请稍后重试");
        }
    }

    /**
     * 更新角色权限和基本信息
     * @param body
     * @return
     */
    @PostMapping("/update")
    public ResultMsg update(@RequestBody String body){
        String oper = "update role";
        try {
            JSONObject jsonObject = JSONObject.parseObject(body);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String remarks = jsonObject.getString("remarks");
            Integer useable = jsonObject.getInteger("useable");
            SysRole sysRole = new SysRole();
            sysRole.setId(id);
            sysRole.setName(name);
            sysRole.setRemarks(remarks);
            sysRole.setStatus(useable);
            List<String> perms = (List)jsonObject.getJSONArray("perms");
            sysRoleService.updateRolePerms(sysRole, perms);
            return ResultMsg.createSuccessMessage("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMsg.createErrorMessage("系统异常，请稍后重试");
        }
    }

    /**
     * 删除一个角色（逻辑删除）
     * @param body
     * @return
     */
    @PostMapping("/delete")
    public ResultMsg delete(@RequestBody String body){
        String oper = "delete role";
        try {
            JSONObject jsonObject = JSONObject.parseObject(body);
            if (jsonObject.getBoolean("isBatch")) {
                List<String> ids = JSONObject.parseArray(jsonObject.getString("ids"), String.class);
                List<SysRole> collect = ids.stream().map((e) -> new SysRole(e, DelFlag.AL_DEL.value())).collect(Collectors.toList());
                sysRoleService.updateBatchById(collect);
            } else {
                String id = jsonObject.getString("id");
                sysRoleService.updateById(new SysRole(id, DelFlag.AL_DEL.value()));
            }
            return ResultMsg.createSuccessMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMsg.createErrorMessage("系统异常，请稍后重试");
        }
    }

}
