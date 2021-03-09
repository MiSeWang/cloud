package com.mrlv;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ActivitiApplicationTests {

    @Resource
    ProcessEngine engineEngine; //注入流程引擎

    @Test
    void test() {
        // 部署流程文件
        DeploymentBuilder builder = engineEngine.getRepositoryService().createDeployment();

        Deployment deploy = builder.addClasspathResource("processes/first.bpmn").deploy();

        System.out.println("部署完成\n"+deploy.getId());
        System.out.println("----------------");
        // 启动流程
    }

}
