package com.hints.authserver.config;

import com.hints.authserver.Util.CodeUtils;
import com.hints.authserver.constant.SecurityConstants;
import com.hints.authserver.dao.UserDao;
import com.hints.authserver.model.CusUserDetails;
import com.hints.authserver.model.Role;
import com.hints.authserver.model.User;
import org.nutz.dao.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String userinfo) throws UsernameNotFoundException {
        String username = null;
        String comp = null;
        String[] user_comp =  userinfo.split("<>");

        /*刷新token时不带公司号，待优化*/
        if(user_comp.length==1){
            username = user_comp[0];
            comp = redisTemplate.opsForValue().get("comp_usid:"+username).toString();
        }else{
            username = user_comp[0];
            comp = user_comp[1];
        }

        Record user = userDao.finduser(username,comp);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在!");
        }

        CusUserDetails userDetails = CusUserDetails.withUsername(user.getString("USID"))
                .password(encoder.encode(CodeUtils.decodeString(user.getString("PAWD"))))
                .comp(comp)
                .authorities(getAuthorities(username,comp))
                .build();

        return userDetails;
    }

    //查询该用户的权限集
    private Collection<? extends GrantedAuthority> getAuthorities(String username,String comp) {
        List<Role> list1 = new ArrayList<>();
        List<Record> list = userDao.getAuth(username,comp);
        for(Map map:list){
            Role role = new Role();
            role.setName(map.get("roid").toString());
            list1.add(role);
        }
        return list1;
    }
}
