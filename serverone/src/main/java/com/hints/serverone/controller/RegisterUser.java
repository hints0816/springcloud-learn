package com.hints.serverone.controller;


import com.hints.serverone.service.Texes000Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
@RestController()
public class RegisterUser {

    @Autowired
    private Texes000Service texes000Service;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/serviceA")
    public HashMap<String,String> service(){
        logger.info("| Waiting:------- push格力互联的审批单开始");
        return texes000Service.getUserBmum();
    }
}
