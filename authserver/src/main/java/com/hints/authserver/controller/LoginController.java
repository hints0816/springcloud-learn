package com.hints.authserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login-success")
    public String loginSuccess(){
        return "登陆成功";
    }

    @RequestMapping(value = "/r/login-success")
    public String r1(){
        return "访问资源1";
    }

    @GetMapping("/r1/r1")
    public String r2(){
        return "访问资源2";
    }

    @GetMapping("/oauth/login1")
    public void login() {
        System.out.println("访问资源3");
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(authentication.toString());
        return "order id : " + id;
    }



}