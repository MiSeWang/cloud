package com.mrlv;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivitiApplicationTests {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private IdentityService identityService;

    @Test
    public void init() {
//        this.initUser();
        this.initGroup();
        this.initMembership();

        System.out.println("部署完成\n");
        System.out.println("----------------");
        // 启动流程
    }

    /**
     * 初始化用户组
     */
    public void initUser() {
        User id1 = identityService.newUser("id1");
        id1.setFirstName("刘备");

        identityService.saveUser(id1);

        User id2 = identityService.newUser("id2");
        id2.setFirstName("诸葛亮");
        identityService.saveUser(id2);
    }

    /**
     * 初始化用户组
     */
    public void initGroup() {
        Group deptLeader = identityService.newGroup("deptLeader");
        deptLeader.setName("deptLeader");
        deptLeader.setType("assignment");

        identityService.saveGroup(deptLeader);

        Group hr = identityService.newGroup("hr");
        hr.setName("hr");
        hr.setType("assignment");

        identityService.saveGroup(hr);
    }

    /**
     *
     */
    public void initMembership(){
        identityService.createMembership("id1", "deptLeader");
        identityService.createMembership("id2", "hr");
    }
}
