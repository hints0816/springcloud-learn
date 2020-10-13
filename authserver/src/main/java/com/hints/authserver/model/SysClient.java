package com.hints.authserver.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("scm_sys_client")
public class SysClient {
    /**
     * 客户端id
     */
    @Name
    private String clientId;

    /**
     * 客户端秘钥
     */
    @Column
    private String clientSecret;

    /**
     * 授权码模式
     */
    @Column
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    @Column
    private String redirectUri;

    /**
     * TOKEN失效时长（秒）
     */
    @Column
    private Double accessTokenValiditySeconds;

    /**
     * REFRESH_TOKEN失效时长（秒）
     */
    @Column
    private Double refreshTokenValiditySeconds;

    /**
     * 作用域
     */
    @Column
    private String scopes;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Double getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Double accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Double getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Double refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }
}
