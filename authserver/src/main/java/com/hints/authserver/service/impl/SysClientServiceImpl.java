package com.hints.authserver.service.impl;


import com.hints.authserver.model.SysClient;
import com.hints.authserver.service.SysClientService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SysClientServiceImpl extends BaseServiceImpl<SysClient> implements SysClientService {
    /*nutz多数据源实体类解决方案：新数据源Dao的注入
    Class<?>  --->  Entity<?> ---> PojoMaker ---> DaoStatement ---> DaoExecutor ---> JDBC

           |               |              |                 |                |
            \------------------------ NutDao 的实现流程 -----------------------/*/
    public SysClientServiceImpl(@Qualifier("sdao")Dao dao) {
        super(dao);
    }

    @Override
    public SysClient findByClientId(String clientId) {
        SysClient sysClient = this.fetch(Cnd.where("clientId", "=", clientId));
        return sysClient;
    }
}
