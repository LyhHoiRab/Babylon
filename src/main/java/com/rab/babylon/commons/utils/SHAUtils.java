package com.rab.babylon.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtils{

    private static final Charset CHARSET = Charset.forName("utf-8");
    private static final String SHA_512 = "SHA-512";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA1 = "SHA1";
    private static final String SHA = "SHA";

    private SHAUtils(){

    }

    private static String encrypt(String str, String algorithm) throws NoSuchAlgorithmException{
        if(StringUtils.isBlank(str)){
            return null;
        }

        MessageDigest sha = MessageDigest.getInstance(algorithm);
        sha.update(str.getBytes(CHARSET));

        byte[] bytes = sha.digest();

        return byte2HexStr(bytes);
    }

    private static String byte2HexStr(byte[] bytes){
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < bytes.length; i++){
            String hex = Integer.toHexString(bytes[i] & 0xFF);

            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

    public static String by512(String str) throws NoSuchAlgorithmException{
        return encrypt(str, SHA_512);
    }

    public static String by256(String str) throws NoSuchAlgorithmException{
        return encrypt(str, SHA_256);
    }

    public static String bySHA1(String str) throws NoSuchAlgorithmException{
        return encrypt(str, SHA1);
    }

    public static String bySHA(String str) throws NoSuchAlgorithmException{
        return encrypt(str, SHA);
    }
}
