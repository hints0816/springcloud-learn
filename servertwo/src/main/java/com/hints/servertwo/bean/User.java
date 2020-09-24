package com.hints.servertwo.bean;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("test0816")
public class User{
    private String USID;
    private String PAWD;
    private String NAME;

    public String getUSID() {
        return USID;
    }

    public void setUSID(String USID) {
        this.USID = USID;
    }

    public String getPAWD() {
        return PAWD;
    }

    public void setPAWD(String PAWD) {
        this.PAWD = PAWD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
