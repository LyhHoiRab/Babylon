package com.rab.babylon.commons.utils;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5Utils{

    private static final Charset CHARSET = Charset.forName("utf-8");
    private static final String MD5 = "MD5";

    private MD5Utils(){

    }

    public static String encrypt(String str) throws Exception{
        if(StringUtils.isBlank(str)){
            return null;
        }

        MessageDigest digest = MessageDigest.getInstance(MD5);
        byte[] buff = digest.digest(str.getBytes(CHARSET));

        return byte2HexStr(buff);
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
}
