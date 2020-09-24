package com.hints.servertwo.controller;

import com.alibaba.fastjson.JSONObject;
import com.hints.servertwo.dao.CipherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CipherController {
    @Autowired
    private CipherDao cipherDao;


    @ResponseBody
    @PostMapping("/servertwo/generateKeyRsa")
    public Object generateKeyRsa(@RequestBody JSONObject jsonParam) throws Exception {
        return JSONObject.toJSONString(cipherDao.generateKeyRsa());
    }

    /*一般来说初始化向量iv都是手动输入比较好的*/
    @ResponseBody
    @PostMapping("/servertwo/generateIvAes")
    public Object generateIvAes(@RequestBody JSONObject jsonParam) throws Exception {
        return cipherDao.generateIvAes();
    }

    @ResponseBody
    @PostMapping("/servertwo/encryptByRsa")
    public String encryptByRsa(@RequestBody JSONObject jsonParam) throws Exception {
        String rsaKey=jsonParam.getString("rsaKey");
        return cipherDao.encryptbyrsa(jsonParam.getString("plainText"),rsaKey);
    }

    @ResponseBody
    @PostMapping("/servertwo/decryptByRsa")
    public String decryptByRsa(@RequestBody JSONObject jsonParam) throws Exception {
        String rsaKey=jsonParam.getString("rsaKey");
        return cipherDao.decryptbyrsa(jsonParam.getString("plainText"),rsaKey);
    }


    /*
    AES对称加密使用的密钥的传递方式：
    1.物理传递：u盾等
    2.第三方KDC分发密钥
    3.非对称加密密钥
    */
    @ResponseBody
    @PostMapping("/servertwo/encryptByAes")
    public String encryptByAes(@RequestBody JSONObject jsonParam) throws Exception {
        String aesKey=jsonParam.getString("aesKey");
        String ivVal=jsonParam.getString("ivVal");
        return cipherDao.encryptbyaes(jsonParam.getString("plainText"),aesKey,ivVal);
    }

    @ResponseBody
    @PostMapping("/servertwo/decryptByAes")
    public String decryptByAes(@RequestBody JSONObject jsonParam) throws Exception {
        String aesKey=jsonParam.getString("aesKey");
        String ivVal=jsonParam.getString("ivVal");
        return cipherDao.decryptbyaes(jsonParam.getString("plainText"), aesKey,ivVal);
    }
}
