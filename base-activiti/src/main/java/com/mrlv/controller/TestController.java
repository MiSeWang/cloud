package com.mrlv.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

//    @Value("${spring.cloud.config.profile}")
    private String profile;

    @GetMapping("/index")
    public String index(){
//        return profile;
        return "";
    }
}
