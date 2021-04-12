package com.hints.authserver.dao;

import com.hints.authserver.Util.DateTime;
import com.hints.authserver.model.Role;
import com.hints.authserver.model.User;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.TableName;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao{
    @Autowired
    private Dao dao;

    @Autowired
    @Qualifier("zdao")
    private Dao zdao;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Record finduser (String usid,String comp){
        Sql sql = Sqls.create("select usid,nama,pawd,suno,(select nama from $smbse02 where suno = t.suno) dsca,grup," +
                "(select dsca from smbse03400 where grup = t.grup) dscb,dept," +
                "(select dsca from smbse04400 where dept = t.dept) dscc," +
                "(select count(*) from $smbse01lock where usid = t.usid) llck," +
                "@comp comp,(select dsca from smcom00 where comp = @comp) dscd,tela," +
                "telb,telc,emil,indt,exdt,lxdt,teno,buyr,isem,eml2 " +
                "from $smbse01 t where t.usid = @usid or t.buyr = @usid");

        sql.setVar("smbse02", "smbse02"+comp)
                .setVar("smbse01lock", "smbse01lock"+comp)
                .setVar("smbse01", "smbse01"+comp)
                .setParam("comp", comp)
                .setParam("usid", usid);

        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(zdao.getEntity(Record.class));
        List<Record> record = zdao.execute(sql).getList(Record.class);
        return record.get(0);
    }

    public List<Record> getAuth (String usid,String comp){
        Record record = finduser(usid,comp);
        Sql sql = Sqls.create("select sb5.roid,sb5.dsca,sb5.rtyp,sb5.pano from smbse05400 sb5," +
                "(select roid from $smbse09 where usid=@usid " +
                "union select roid from smbse12400 where grup=@grup " +
                "union select roid from smbse13400 where dept=@dept " +
                "union select roid from $smbse14 where duid=@duid " +
                "and to_char(edat,'yyyy/MM/dd hh24:mi:ss')>@ttime) smrole where sb5.roid=smrole.roid");

        sql.setVar("smbse09", "smbse09"+comp)
                .setVar("smbse14", "smbse14"+comp)
                .setParam("usid", record.getString("usid"))
                .setParam("grup", record.getString("grup"))
                .setParam("dept", record.getString("dept"))
                .setParam("duid", record.getString("usid"))
                .setParam("ttime", DateTime.getStringNowTime("yyyy/MM/dd HH:mm:ss"));
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(zdao.getEntity(Record.class));
        List<Record> records = zdao.execute(sql).getList(Record.class);
        return records;
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
