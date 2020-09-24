package com.hints.servertwo.controller;

import com.alibaba.fastjson.JSONObject;
import com.hints.servertwo.bean.User;
import com.hints.servertwo.dao.CronTaskDao;
import com.hints.servertwo.service.UserService;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController()
public class GetOneUser {

    @Autowired
    private UserService userservice;

    @Autowired
    private CronTaskDao cronTaskDao;

    @Value("${server.port}")
    String port;

    @GetMapping("/user/{id}")
    public String getOneUserById(@PathVariable String id) {
        System.out.println("get");
        return cronTaskDao.getTest()+port;
    }

    @GetMapping("/order/{id}")
    public String getOneUserById2(@PathVariable String id,HttpServletRequest request) {
        System.err.println(request.getHeader("NAME"));
        return cronTaskDao.getTest()+port;
    }

    @GetMapping("/user/insert")
    public void insertUser() {
        User user = new User();
        user.setUSID("0002");
        user.setPAWD("123");
        user.setNAME("15");
        userservice.insertUser(user);
    }





    @GetMapping("/user/test")
    public void testUser() {
        cronTaskDao.getTest();
    }

    @GetMapping("/user/test2")
    public void testUser2() {
        cronTaskDao.getTest2();
    }
}

