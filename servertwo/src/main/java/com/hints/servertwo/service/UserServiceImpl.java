package com.hints.servertwo.service;

import com.hints.servertwo.bean.User;
import com.hints.servertwo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;

    public HashMap<String,String> queryUser(String id){
        return userdao.SelectBmum(id);
    }

    public void insertUser(User user){
        userdao.InsertUser(user);
    }
}
