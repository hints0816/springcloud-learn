package com.hints.serverone.controller;

import com.hints.serverone.export_api.FeignToGetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class GetUser {
    @Autowired
    private FeignToGetUser ftuser;

    @GetMapping("timodgetuser/{id}")
//    @HystrixCommand(fallbackMethod = "getUserBackUp")
    public String queryUserById(@PathVariable String id){
        return this.ftuser.getUser(id);
    }


    @GetMapping("timodgetuser/insert")
//    @HystrixCommand(fallbackMethod = "getUserBackUp")
    public void insertUser(){
        this.ftuser.insertUser();
    }


//    public String getUserBackUp(String id){
//        return "false";
//    }
}
