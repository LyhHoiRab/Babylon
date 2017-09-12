package com.rab.babylon.commons.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件处理工具
 */
public class FileUtils{

    //文件路径分隔符
    public static final String FILE_PATH_SEPARATOR = File.separator;
    //文件后缀分隔符
    public static final String FILE_SUFFIX_SEPARATOR = ".";

    private FileUtils(){

    }

    /**
     * 获取文件MD5值
     */
    public static String getMD5(File file) throws IOException{
        if(file == null){
            throw new IllegalArgumentException("文件不能为空");
        }

        FileInputStream fis = new FileInputStream(file);

        return DigestUtils.md5Hex(fis);
    }

    /**
     * 获取数组MD5值
     */
    public static String getMD5(byte[] bytes){
        if(bytes == null || bytes.length < 1){
            throw new IllegalArgumentException("文件不能为空");
        }

        return DigestUtils.md5Hex(bytes);
    }

    /**
     * 根据MD5值判断文件是否相同
     */
    public static boolean isSame(File file1, File file2) throws IOException{
        if(file1 == null || file2 == null){
            throw new IllegalArgumentException("文件不能为空");
        }

        return getMD5(file1).equalsIgnoreCase(getMD5(file2));
    }

    /**
     * 根据MD5值判断数组是否相同
     */
    public static boolean isSame(byte[] bytes1, byte[] bytes2){
        if(bytes1 == null
                || bytes1.length < 1
                || bytes2 == null
                || bytes2.length < 1){
            throw new IllegalArgumentException("文件不能为空");
        }

        return getMD5(bytes1).equalsIgnoreCase(getMD5(bytes2));
    }
}
