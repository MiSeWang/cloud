package com.mrlv.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Task;

import java.io.IOException;
import java.util.List;

public interface ITestService {

    String startProcesses(String bizId);

    List<Task> findTasksByUserId(String userId);


    void completeTask(String taskId,String userId,String result);


    void updateBizStatus(DelegateExecution execution, String status);

    void queryProImg(String processInstanceId) throws IOException;

    void queryProHighLighted(String processInstanceId) throws IOException;
}
