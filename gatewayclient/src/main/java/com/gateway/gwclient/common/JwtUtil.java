package com.gateway.gwclient.common;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static final String SECRET = "internet_plus";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String LOGIN_URL = "/token/userId/pwd";
    public static final String LOGOUT_URL = "/token/userId";
    public static final String HEADER_AUTH = "authorization";
    public static final String HEADER_USERID = "userid";
    //token超时时间
    public static final int TOKEN_EXPIRATION_MINUTE = 30;
    //token的redis超时时间
    public static final int TOKEN_REDIS_EXPIRATION_DAY = 7;


    public static String generateToken(String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, TOKEN_EXPIRATION_MINUTE); //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.format(date);
        //todo 优化token的生层规则
        HashMap<String, Object> map = new HashMap<>();
        map.put(HEADER_USERID, userId);
        String jwt = Jwts.builder()
                .setSubject(HEADER_USERID).setClaims(map)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return TOKEN_PREFIX + " " + jwt;
    }

    /*jwt解密：使用授权服务的签名对jwt进行解密*/
    public static Map<String, String> validateToken(String token) {
        HashMap<String, String> tokenMap = new HashMap<String, String>();
        if (StringUtils.isEmpty(token)) {
            return tokenMap;
        }
        try {
            Map<String, Object> tokenBody = Jwts.parser()
                    .setSigningKey(SECRET.getBytes("utf-8"))
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String userId = String.valueOf(tokenBody.get(HEADER_USERID));
            tokenMap.put(HEADER_USERID, userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tokenMap;
    }

    /**
     * 移到jwtUtil中去
     *
     * @param token
     * @return
     */
    public static Map<String, String> validateTokenAndUser(String token, String userIdIn) {
        Map<String, String> tokenResultMap = new HashMap<>();
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userIdIn)) {
            return tokenResultMap;
        }
        tokenResultMap = validateToken(token);
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userIdIn)) {
            return tokenResultMap;
        }
        //判断传入的userid和token是否匹配
        String userIdOri = tokenResultMap.get(HEADER_USERID);
        if (!userIdIn.equals(userIdOri)) {
            return new HashMap<String,String>();
        }
        return tokenResultMap;
    }

}