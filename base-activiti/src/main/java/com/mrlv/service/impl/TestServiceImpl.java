package com.mrlv.service.impl;

import com.mrlv.service.ITestService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TestServiceImpl implements ITestService {

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
     * 用户管理
     */
    @Resource
    private IdentityService identityService;

    @Resource
    private ProcessEngineConfiguration processEngineConfiguration;

    /**
     * 申请一个流程
     * @param bizId
     */
    @Override
    public String startProcesses(String bizId) {
        String zdy = "自定义id";
        //用来设置自动流程的人员id，引擎会自动把用户的id保存到实例中
        identityService.setAuthenticatedUserId(zdy);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("demo5", bizId);//流程图id，业务表id
        //生成实例
        System.out.println("流程启动成功，流程id:" + instance.getId());
        return instance.getId();
    }

    /**
     * 描述: 根据用户id查询待办任务列表
     * @param userId
     * @return
     */
    @Override
    public List<Task> findTasksByUserId(String userId) {

        List<Task> resultTask = taskService.createTaskQuery()
                .processDefinitionKey("demo5")  //获取指定流程的代办
//                .taskAssignee(userId)         //根据当前人的id查询
//                .taskCandidateUser(userId)    //获取当前任未签收的
                .taskCandidateOrAssigned(userId)
                .active()                       //活动的
                .list();
        return resultTask;
    }

    /**
     * 描述:任务审批 	（通过/拒接）
     * @param taskId
     * @param userId
     * @param result
     */
    @Override
    public void completeTask(String taskId, String userId, String result) {
        //声明是哪个任务。
        taskService.claim(taskId, userId);
        Map<String,Object> vars = new HashMap<>();
        //添加属性
        vars.put("sign", "true");
        taskService.complete(taskId, vars);
    }

    /**
     * 更改业务流程状态#{ActivityDemoServiceImpl.updateBizStatus(execution,"tj")}
     * @param execution
     * @param status
     */
    @Override
    public void updateBizStatus(DelegateExecution execution, String status) {
        String bizId = execution.getProcessInstanceBusinessKey();
        //根据业务id自行处理业务表
        System.out.println("业务表["+bizId+"]状态更改成功，状态更改为："+status);
    }


    @Override
    public void queryProImg(String processInstanceId) throws IOException {
        //获取历史流程实例
        HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        //根据流程定义获取输入流
        InputStream is = repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
        BufferedImage bi = ImageIO.read(is);
        File file = new File("demo2.png");
        if(!file.exists()) file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ImageIO.write(bi, "png", fos);
        fos.close();
        is.close();
        System.out.println("图片生成成功");

        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("userId").list();
        for(Task t : tasks) {
            System.out.println(t.getName());
        }
    }

    @Override
    public void queryProHighLighted(String processInstanceId) throws IOException {

    }

}
