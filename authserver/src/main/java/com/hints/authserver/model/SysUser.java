package com.hints.authserver.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.time.LocalDateTime;


@Table("scm_sys_user")
public class SysUser {
    /**
     * 用户ID
     */
    @Id
    private int userId;

    /**
     * 用户名
     */
    @Column
    private String userName;

    /**
     * 用户中文名称
     */
    @Column
    private String chineseName;

    /**
     * 密码
     */
    @Column
    private String pwd;

    /**
     * 登录IP
     */
    @Column
    private String loginIp;

    /**
     * 部门ID
     */
    @Column
    private String depId;

    /**
     * 部门名称
     */
    @Column
    private String depName;

    /**
     * 是否可用,0=否;1=是
     */
    @Column
    private int isEnable;

    /**
     * 是否过期,0=否;1=是
     */
    @Column
    private int accountNonExpired;

    /**
     * 是否锁定,0=否;1=是
     */
    @Column
    private int accountNonLocked;

    /**
     * 证书是否可用,0=否;1=是
     */
    @Column
    private int credentialsNonExpired;

    /**
     * 邮箱
     */
    @Column
    private String mail;

    /**
     * 头像路径
     */
    @Column
    private String avatar;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createDt;

    /**
     * 最后登录时间
     */
    @Column
    private LocalDateTime lastLoginDt;

    /**
     * 失效时期
     */
    @Column
    private LocalDateTime deadlineDt;

    /**
     * 更新时间
     */
    @Column
    private LocalDateTime updateDt;

    /**
     * 简介
     */
    @Column
    private String introduction;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public int getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(int accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public int getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(int accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public int getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(int credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    public LocalDateTime getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(LocalDateTime lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public LocalDateTime getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(LocalDateTime deadlineDt) {
        this.deadlineDt = deadlineDt;
    }

    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(LocalDateTime updateDt) {
        this.updateDt = updateDt;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
