package com.hints.authserver.constant;

import java.util.concurrent.TimeUnit;

public final class SecurityConstants {

    private SecurityConstants() {
    }

    /**
     * client缓存有效时间
     */
    public static final long CLIENT_CACHE_TIMEOUT = 30;

    /**
     * client缓存有效时间单位
     */
    public static final TimeUnit CLIENT_CACHE_TIMEOUT_UNIT = TimeUnit.MINUTES;

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    public static final String CACHE_CLIENT_KEY = "oauth_client_details";
}
