package com.hints.nettylearn.controller;

import com.hints.nettylearn.dao.NettyDao;
import org.nutz.dao.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NettyController {
    @Autowired
    NettyDao nettyDao;


    @GetMapping("/hello")
    public String getGcpComps() {
        List<Record> gcpComps = nettyDao.getGcpComps();

        return "hello";
    }
}