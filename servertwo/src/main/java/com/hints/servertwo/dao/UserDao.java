package com.hints.servertwo.dao;

import com.hints.servertwo.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface UserDao {
    @Select("SelectBmum")
    public HashMap<String,String> SelectBmum(String ID);
    @Insert("InsertUser")
    public void InsertUser(User user);
}
