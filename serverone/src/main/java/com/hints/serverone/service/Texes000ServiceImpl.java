package com.hints.serverone.service;

import com.hints.serverone.dao.Texes000Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Texes000ServiceImpl implements Texes000Service {
    @Autowired
    private Texes000Dao txdao;

    public HashMap<String,String> getUserBmum(){
        System.out.println("x");

        HashMap<String,String> list = txdao.SelectBmum("hints5");
        return list;
    }
}
