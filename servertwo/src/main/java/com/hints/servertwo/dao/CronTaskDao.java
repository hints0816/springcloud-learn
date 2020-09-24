package com.hints.servertwo.dao;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


@Repository
public class CronTaskDao {
    @Autowired
    private Dao dao;

    @Autowired
    @Qualifier("sdao")
    private Dao sdao;

    private String src = "签名";
    /* 加密使用的 key */
    private static final String AES_KEY = "KUbHwTqBy6TBQ2gN";
    /* 加密使用的 IV */
    private static final String AES_IV = "pIbF6GR3XEN1PG05";
    public String getTest(){
        Sql sql = Sqls.create("select NAME from test0816 where usid='hints5'");
        sql.setCallback(Sqls.callback.str());
        String name = dao.execute(sql).getString();
       return name;
    }

    public String getTest2(){
        Sql sql = Sqls.create("select usid from smflw07400 where prid='LXCG00000020'");
        sql.setCallback(Sqls.callback.str());
        String name = sdao.execute(sql).getString();
        return name;
    }

    public void getTest3(){
        Trans.exec(Connection.TRANSACTION_READ_COMMITTED,new Atom() {
            @Override
            public void run() {
                sdao.insert("specialpay04", Chain.make("comp","999").add("dsca","测试基地"));
                sdao.insert("specialpay04", Chain.make("comp","970").add("dsca","测试基地"));
            }});
    }


}
