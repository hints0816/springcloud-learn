package com.hints.authserver.dao;

import com.hints.authserver.model.Role;
import com.hints.authserver.model.User;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.TableName;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao{
    @Autowired
    private Dao dao;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User finduser (String userid){
        User user = dao.fetch(User.class, userid);
        return user;
    }

    public List<Record> getAuth (String username){
        Sql sql = Sqls.create("select t2.name from $table1 t1,$table2 t2,$table3 t3 " +
                "where t1.usid = t3.user_id and t2.id = t3.role_id and t1.usid = @username");

        sql.setVar("table1", "test0816")
                .setVar("table2", "testrole")
                .setVar("table3", "testuserrole")
                .setParam("username", username);
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao.getEntity(Record.class));
        List<Record> record = dao.execute(sql).getList(Record.class);
        return record;
    }

    public void createUser(String userid,String username,String password) {
        User user=new User();
        user.setUSID(userid);
        String hash = encoder.encode(password);
        user.setPAWD(hash);
        user.setNAME(username);
        dao.insert(user);
    }

    public String getclientname(String clientid) {
        if("client_3".equals(clientid)){
            return "格力G平台";
        }
        return "";
    }
}
