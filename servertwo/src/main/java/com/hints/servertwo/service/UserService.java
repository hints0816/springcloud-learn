package com.hints.servertwo.service;

import com.hints.servertwo.bean.User;
import java.util.HashMap;

public interface UserService {
    public HashMap<String,String> queryUser(String id);

    public void insertUser(User user);
}
