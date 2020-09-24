package com.hints.serverthree.controller;

import com.hints.serverthree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController()
public class GetUser {
    @Autowired
    private UserService userservice;
    @GetMapping("getexcel")
    public String queryUserById(@PathVariable String id){
        return id;
    }

    @RequestMapping(value = "/discovery")
    public OutputStream queryUserById2(@RequestParam String id, HttpServletResponse response){
        OutputStream out = null;

        return out;
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/index");
        return mav;
    }
}
