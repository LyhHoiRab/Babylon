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

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(buff);
    }
}
