package com.mrlv.controller;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 流程引擎， 在Activiti中最核心的类，其他的类都是由他而来。
     */
    @Resource
    ProcessEngine engineEngine;

    /**
     * 管理流程定义
     */
    @Resource
    private RepositoryService repositoryService;

    /**
     * 执行管理，包括启动、推进、删除流程实例等操作
     */
    @Resource
    private RuntimeService runtimeService;

    /**
     * 任务管理
     */
    @Resource
    private TaskService taskService;

    /**
     * 历史管理(执行完的数据的管理)
     */
    @Resource
    private HistoryService historyService;

    /**
     * 组织机构管理
     */
    @Resource
    private IdentityService IdentityService;

    /**
     * 一个可选服务，任务表单管理
     */
    @Resource
    private FormService formService;

    @PostMapping("init")
    public String init(){
        // 部署流程文件
        DeploymentBuilder builder = engineEngine.getRepositoryService().createDeployment();
        Deployment deploy = builder.addClasspathResource("processes/simple.bpmn").deploy();
        System.out.println("部署完成\n"+deploy.getId());
        System.out.println("----------------");
        // 启动流程
        return "ok";
    }

    @PostMapping("start")
    public String start(){
        String instanceKey = "myProcess_1";
        logger.info("开启请假流程...");
        Map<String, Object> map = new HashMap<String, Object>();
        //在holiday.bpmn中,填写请假单的任务办理人为动态传入的userId,此处模拟一个id
        map.put("userId", "10001");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(instanceKey, map);
        logger.info("启动流程实例成功:{}", instance);
        logger.info("流程实例ID:{}", instance.getId());
        logger.info("流程定义ID:{}", instance.getProcessDefinitionId());
        return instance.getId();
    }

    @PostMapping("employeeApply")
    public String employeeApply(@RequestParam("id") String instanceId) {
        if (StringUtils.isBlank(instanceId)) return "NO";
        String leaveDays = "10"; // 请假天数
        String leaveReason = "回老家结婚"; // 请假原因
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        if (task == null) {
            logger.info("任务ID:{}查询到任务为空！", instanceId);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("days", leaveDays);
        map.put("date", new Date());
        map.put("reason", leaveReason);
        taskService.complete(task.getId(), map);
        logger.info("执行【员工申请】环节，流程推动到【上级审核】环节");
        return "YES";
    }


    @PostMapping("showTaskVariable")
    public void showTaskVariable (@RequestParam("id") String instanceId){
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        String days = (String) taskService.getVariable(task.getId(), "days");
        Date date = (Date) taskService.getVariable(task.getId(), "date");
        String reason = (String) taskService.getVariable(task.getId(), "reason");
        String userId = (String) taskService.getVariable(task.getId(), "userId");
        System.out.println("请假天数:  " + days);
        System.out.println("请假理由:  " + reason);
        System.out.println("请假人id:  " + userId);
        System.out.println("请假日期:  " + date.toString());
    }


    @PostMapping("departmentAudit")
    public void departmentAudit(@RequestParam("id") String instanceId) {
        String departmentalOpinion = "早去早回";
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentalOpinion", departmentalOpinion);
        taskService.complete(task.getId(), map);
        logger.info("添加审批意见,请假流程结束");
    }


    @GetMapping("/index")
    public String index(){
//        return profile;
        return "";
    }

}
