package com.hints.nettylearn.dao;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NettyDao {
    @Autowired
    private Dao dao;

    public List<Record> getGcpComps() {
        Sql sql = Sqls.create("select * from $table");
        sql.setVar("table", "cbase012");
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao.getEntity(Record.class));
        List<Record> gcpComps = dao.execute(sql).getList(Record.class);
        return gcpComps;
    }
}

