package com.rab.commons.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * AES加密算法
 * 支持128,192,256位加解密
 */
public class AESUtils{

    private static final Charset CHARSET = Charset.forName("utf-8");
    private static final String AES = "AES";
    private static final int AES_128 = 128;
    private static final int AES_192 = 192;
    private static final int AES_256 = 256;

    public static String encryptBy128(String str, String password) throws Exception{
        return encrypt(str, password, AES_128);
    }

    public static String encryptBy192(String str, String password) throws Exception{
        return encrypt(str, password, AES_192);
    }

    public static String encryptBy256(String str, String password) throws Exception{
        return encrypt(str, password, AES_256);
    }

    public static String decryptBy128(String str, String password) throws Exception{
        return decrypt(str, password, AES_128);
    }

    public static String decryptBy192(String str, String password) throws Exception{
        return decrypt(str, password, AES_192);
    }

    public static String decryptBy256(String str, String password) throws Exception{
        return decrypt(str, password, AES_256);
    }

    private static String encrypt(String str, String password, int length) throws Exception{
        if(StringUtils.isBlank(str)){
            throw new IllegalArgumentException("加密内容不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new IllegalArgumentException("加密密钥不能为空");
        }
        if(length < 1){
            throw new IllegalArgumentException("密钥长度不能少于1");
        }

        return byte2HexStr(encryptToByte(str, password, length));
    }

    private static String decrypt(String str, String password, int length) throws Exception{
        if(StringUtils.isBlank(str)){
            throw new IllegalArgumentException("解密内容不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new IllegalArgumentException("解密密钥不能为空");
        }
        if(length < 1){
            throw new IllegalArgumentException("密钥长度不能少于1");
        }

        byte[] decrypt = hexStr2Byte(str);
        return new String(decryptToByte(decrypt, password, length), CHARSET);
    }

    private static byte[] encryptToByte(String str, String password, int length) throws Exception{
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        kgen.init(length, new SecureRandom(password.getBytes(CHARSET)));

        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();

        SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);

        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] bytes = str.getBytes(CHARSET);

        return cipher.doFinal(bytes);
    }

    private static byte[] decryptToByte(byte[] bytes, String password, int length) throws Exception{
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        kgen.init(length, new SecureRandom(password.getBytes(CHARSET)));

        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();

        SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);

        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(bytes);
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
