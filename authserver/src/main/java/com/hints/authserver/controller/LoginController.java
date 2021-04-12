package com.hints.authserver.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.nutz.lang.util.NutMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/oauth/1")
    public String getOrder(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(authentication.toString());
        return "order id : " + id;
    }

    /*授权码模式对外接口*/
    @PostMapping("/oauth2/loginCommon")
    public Object loginCommon(@RequestBody JSONObject jsonobject) {
        HashMap<String,String> param=new HashMap<String, String>();
        param.put("session[email_or_mobile_number]",jsonobject.getString("account"));
        param.put("session[password]",jsonobject.getString("password"));
        Cookie cookie = doGet("http://dq18-180686j.it2004.gree.com.cn:9091/oauth/authorize?response_type=code&redirect_uri=http%3A%2F%2Fdq18-180686j.it2004.gree.com.cn%3A8765%2Foauth%2FcallbackCoe&state="+jsonobject.getString("state")+"&client_id="+jsonobject.getString("client_id"));
        HashMap map = doPost("http://dq18-180686j.it2004.gree.com.cn:9091/oauth/login",param,cookie);
        String result = doPost2(map.get("result").toString(),(Cookie)map.get("cookie"));
        String code = result.substring(result.indexOf("code=") + "code=".length(), result.indexOf("&state"));
        String state = result.substring(result.indexOf("state=") + "state=".length(), result.length());
        return NutMap.NEW().addv("code",code).addv("state",state);
    }

    public Cookie doGet(String url) {
        Cookie resCookie = null;
        BasicCookieStore store= new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            List<Cookie> cookielist = store.getCookies();
            for(Cookie cookie: cookielist){
                String name=cookie.getName();
                if("JSESSIONID".equals(name)){
                    resCookie = cookie;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resCookie;
    }

    public static HashMap doPost(String url, Map<String, String> param, Cookie cookie) {
        Cookie rescookie = null;
        BasicCookieStore store= new BasicCookieStore();
        store.addCookie(cookie);
        CloseableHttpClient httpClient =  HttpClients.custom().setDefaultCookieStore(store).build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 302) {
                Header header = response.getFirstHeader("Location");
                String location = header.getValue();
                resultString = location.substring(location.indexOf("=") + 1, location.length());
            }else{
                List<Cookie> cookielist = store.getCookies();
                for(Cookie cookie1: cookielist){
                    String name=cookie1.getName();
                    if("JSESSIONID".equals(name)){
                        rescookie = cookie1;
                    }
                }
                String content = EntityUtils.toString(response.getEntity());
                resultString = content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("cookie",rescookie);
        JSONObject jsonObject =JSONObject.parseObject(resultString);
        map.put("result",jsonObject.getString("targetUrl"));
        return map;
    }

    public static String doPost2(String url, Cookie cookie) {
        BasicCookieStore store= new BasicCookieStore();
        store.addCookie(cookie);
        CloseableHttpClient httpClient =  HttpClients.custom().setDefaultCookieStore(store).build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 302) {
                Header header = response.getFirstHeader("Location");
                String location = header.getValue();
                resultString = location.substring(location.indexOf("?") + 1, location.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}