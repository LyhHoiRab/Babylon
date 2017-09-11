package com.rab.commons.utils;

import it.sauronsoftware.base64.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils{

    //加密算法
    private static final String RSA = "RSA";
    //签名算法
    private static final String SIGN = "MD5withRSA";
    //公钥
    public static final String PUBLIC_KEY = "RSA_PUBLIC_KEY";
    //私钥
    public static final String PRIVATE_KEY = "RSA_PRIVATE_KEY";
    //最大明文长度
    private static final int MAX_ENCRYPT_BLOCK = 117;
    //最大密文长度
    private static final int MAX_DECRYPT_BLOCK = 238;

    private RSAUtils(){

    }

    /**
     * 生成密钥对
     */
    public static Map<String, Object> getKeyPair() throws Exception{
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }

    /**
     * 私钥解密
     */
    public static String decryptByPrivateKey(String encode, String privateKey) throws Exception{
        byte[] data = hexStr2Byte(encode);
        return new String(decryptByPrivateKey(data, privateKey));
    }

    /**
     * 公钥解密
     */
    public static String decrypteByPublicKey(String encode, String publicKey) throws Exception{
        byte[] data = hexStr2Byte(encode);
        return new String(decrypteByPublicKey(data, publicKey));
    }

    /**
     * 私钥加密
     */
    public static String encryptByPrivateKey(String str, String privateKey) throws Exception{
        byte[] data = str.getBytes();
        return byte2HexStr(encryptByPrivateKey(data, privateKey));
    }

    /**
     * 公钥加密
     */
    public static String encryptyPublicKey(String str, String publicKey) throws Exception{
        byte[] data = str.getBytes();
        return byte2HexStr(encryptByPublicKey(data, publicKey));
    }

    /**
     * 用私钥对加密信息生成数字签名
     */
    public static String sign(String encode, String privateKey) throws Exception{
        byte[] data = hexStr2Byte(encode);
        return sign(data, privateKey);
    }

    /**
     * 用私钥对加密信息生成数字签名
     */
    public static String sign(byte[] bytes, String privateKey) throws Exception{
        byte[] privateKeyByte = Base64.decode(privateKey.getBytes());

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance(SIGN);
        signature.initSign(privateK);
        signature.update(bytes);

        return new String(Base64.encode(signature.sign()));
    }

    /**
     * 校验数字签名
     */
    public static boolean verfiy(String encode, String publicKey, String sign) throws Exception{
        byte[] data = hexStr2Byte(encode);
        return verify(data, publicKey, sign);
    }

    /**
     * 校验数字签名
     */
    public static boolean verify(byte[] bytes, String publicKey, String sign) throws Exception{
        byte[] publicKeyByte = Base64.decode(publicKey.getBytes());

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicK = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGN);
        signature.initVerify(publicK);
        signature.update(bytes);

        return signature.verify(Base64.decode(sign.getBytes()));
    }

    /**
     * 私钥解密
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception{
        byte[] privateKeyByte = Base64.decode(privateKey.getBytes());

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);

        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        //对数据分段解密
        while(inputLen - offSet > 0){
            if(inputLen - offSet > MAX_DECRYPT_BLOCK){
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            }else{
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();

        return decryptedData;
    }

    /**
     * 公钥解密
     */
    public static byte[] decrypteByPublicKey(byte[] data, String publicKey) throws Exception{
        byte[] publickeyByte = Base64.decode(publicKey.getBytes());

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publickeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key publicK = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = data.length;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        //对数据分段解密
        while(inputLen - offSet > 0){
            if(inputLen - offSet > MAX_DECRYPT_BLOCK){
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            }else{
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();

        return decryptedData;
    }

    /**
     * 公钥加密
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception{
        byte[] publickeyByte = Base64.decode(publicKey.getBytes());

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publickeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key publicK = keyFactory.generatePublic(x509KeySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);

        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        //对数据分段加密
        while(inputLen - offSet > 0){
            if(inputLen - offSet > MAX_ENCRYPT_BLOCK){
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            }else{
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();

        return encryptedData;
    }

    /**
     * 私钥加密
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception{
        byte[] privatekeyByte = Base64.decode(privateKey.getBytes());

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privatekeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);

        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        //对数据分段加密
        while(inputLen - offSet > 0){
            if(inputLen - offSet > MAX_ENCRYPT_BLOCK){
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            }else{
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();

        return encryptedData;
    }

    /**
     * 获取私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return new String(Base64.encode(key.getEncoded()));
    }

    /**
     * 获取公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception{
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return new String(Base64.encode(key.getEncoded()));
    }

    private static String byte2HexStr(byte bytes[]){
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < bytes.length; i++){
            String hex = Integer.toHexString(bytes[i] & 0xFF);

            if(hex.length() == 1){
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    private static byte[] hexStr2Byte(String hexStr){
        int length = hexStr.length() / 2;
        byte[] result = new byte[length];

        for(int i = 0; i < length; i++){
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }

        return result;
    }
}
