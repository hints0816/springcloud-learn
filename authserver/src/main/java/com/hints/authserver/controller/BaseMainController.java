package com.hints.authserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.hints.authserver.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("authorizationRequest")
public class BaseMainController {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserDao userDao;

    @RequestMapping("/oauth/index")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Map requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String c = (String) request.getAttribute("error");
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest) {
            String targetUrl = savedRequest.getRedirectUrl();
            String param = "";
            if(targetUrl.contains("?")){
                param = targetUrl.substring(targetUrl.indexOf('?'));
            }
            System.out.println("引发跳转的请求是:" + targetUrl);
            //这里我必须改变跳转的url，要是使用targetUrl会造成循环
            redirectStrategy.sendRedirect(request, response, "/oauth2/authorize"+param);
        }
        //如果访问的是接口资源
        return new HashMap() {{
            put("code", 401);
            put("msg", "访问的服务需要身份认证，请引导用户到登录页");
        }};
    }

    @RequestMapping(value = "/oauth2/authorize")
   /* @ResponseBody*/
    public ModelAndView oauthLogin(Model model2, @RequestParam("response_type") String response_type,
                                   @RequestParam("redirect_uri") String redirect_uri,
                                   @RequestParam("client_id") String client_id, HttpServletResponse response, HttpServletRequest request){
        ModelAndView model = new ModelAndView("base-login");
        if(request.getAttribute("error")!=null){
            model.addObject("error",request.getAttribute("error"));
        }
        model.addObject("client_name",userDao.getclientname(client_id));
        return model;
    }

    @RequestMapping(value = "/oauth2/error")
    @ResponseBody
    public JSONObject loginError(HttpServletResponse response, HttpServletRequest request){
//        ModelAndView model = new ModelAndView("base-login");
//        if(request.getAttribute("error")!=null){
//            model.addObject("error",request.getAttribute("error"));
//        }
//        model.addObject("client_name",userDao.getclientname(client_id));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error",(String) request.getAttribute("error"));
        return jsonObject;
    }

    @RequestMapping(value = "/oauth2/success")
    @ResponseBody
    public JSONObject loginSuccess(HttpServletResponse response, HttpServletRequest request){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("targetUrl",(String) request.getAttribute("targetUrl"));
        return jsonObject;
    }

    @GetMapping("/oauth/register")
    public String registerPage(){
        return "base-register";
    }

    @GetMapping("/oauth/index231")
    public String getOrder2() {
        return "index223";
    }
}