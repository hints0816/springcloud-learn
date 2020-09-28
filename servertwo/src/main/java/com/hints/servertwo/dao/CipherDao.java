package com.hints.servertwo.dao;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Repository;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
@Repository
public class CipherDao {
    public Map<String, String> generateKeyRsa(){
        KeyPairGenerator key = null;
        Map<String, String> keyPairMap = new HashMap<String, String>();
        try {
            key = KeyPairGenerator.getInstance("RSA");
            key.initialize(1024);
            KeyPair pair = key.generateKeyPair();
            RSAPublicKey rsapublickey = (RSAPublicKey) pair.getPublic();
            RSAPrivateKey rsaprivatekey = (RSAPrivateKey) pair.getPrivate();

            System.out.println(new String(Base64.encodeBase64(rsapublickey.getEncoded())));
            System.out.println(new String(Base64.encodeBase64(rsaprivatekey.getEncoded())));
            keyPairMap.put("publicKey",  new String(Base64.encodeBase64(rsapublickey.getEncoded())));
            keyPairMap.put("privateKey",  new String(Base64.encodeBase64(rsaprivatekey.getEncoded())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPairMap;
    }

    public String generateIvAes(){
        IvParameterSpec ivParams = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] iv = new byte[cipher.getBlockSize()];
            randomSecureRandom.nextBytes(iv);
            ivParams = new IvParameterSpec(iv);
            System.out.println(ivParams.getIV());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ivParams.getIV().toString();
    }

    public String encryptbyrsa(String plainText,String key) throws Exception {
        //X509EncodedKeySpec类使用X.509标准作为密钥规范管理的编码格式
        byte[] decoded = Base64.decodeBase64(key);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] data = plainText.getBytes("UTF-8");
        // 加密时超过117字节就报错。为此采用分段加密的办法来加密
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        String outStr = new String(Base64.encodeBase64(decryptedData),"UTF-8");
        return outStr;
    }

    public String decryptbyrsa(String plainText,String key) throws Exception {
        byte[] inputByte = Base64.decodeBase64(plainText.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(key);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        int inputLen = inputByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(inputByte, offSet, 128);
            } else {
                cache = cipher.doFinal(inputByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 128;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }

    private static AlgorithmParameters generateIV(String ivVal) throws Exception{
        byte[]iv=ivVal.getBytes();
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }

    public String encryptbyaes(String plainText,String aesKey,String ivVal) throws Exception {
        byte[] data=plainText.getBytes("UTF-8");
        AlgorithmParameters iv=generateIV(ivVal);
        byte[] keyBytes = aesKey.getBytes();
        //转化为密钥
        SecretKeySpec key = new SecretKeySpec(keyBytes,"AES");
        /*在java中用aes256进行加密，但是发现java里面不能使用PKCS7Padding，而java中自带的是PKCS5Padding填充，那解决办法是，通过BouncyCastle组件来让java里面支持PKCS7Padding填充。*/
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        //设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key,iv);
        byte[] encryptData= cipher.doFinal(data);
        return  Base64.encodeBase64String(encryptData);
    }

    public String decryptbyaes(String encryptedStr,String aesKey,String ivVal) throws Exception{
        byte[] encryptedData=Base64.decodeBase64(encryptedStr);
        byte[] keyBytes = aesKey.getBytes();
        SecretKeySpec key = new SecretKeySpec(keyBytes,"AES");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        AlgorithmParameters iv=generateIV(ivVal);
        //设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key,iv);
        byte[] decryptData=cipher.doFinal(encryptedData);
        return new String(decryptData);
    }

}
