package com.hints.authserver.config;

import com.hints.authserver.dao.UserDao;
import com.hints.authserver.model.Role;
import com.hints.authserver.model.User;
import org.nutz.dao.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.finduser(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在!1");
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(username))
                .build();
        return userDetails;
    }

    //查询该用户的权限集
    public Collection<? extends GrantedAuthority> getAuthorities(String username) {
        List<Role> list1 = new ArrayList<>();
        List<Record> list = userDao.getAuth(username);
        for(Map map:list){
            Role role = new Role();
            role.setName(map.get("name").toString());
            list1.add(role);
        }
        return list1;
    }
}
