package com.rab.babylon.commons.utils;

import eu.bitwalker.useragentutils.*;
import org.apache.commons.lang3.StringUtils;

/**
 * 浏览器工具
 */
public class UserAgentUtils{

    private UserAgentUtils(){

    }

    /**
     * 获取客户端信息
     */
    public static UserAgent getUserAgent(String agentInfo){
        if(StringUtils.isBlank(agentInfo)){
            throw new IllegalArgumentException("客户端信息不能为空");
        }

        return UserAgent.parseUserAgentString(agentInfo);
    }

    /**
     * 获取客户端浏览器信息
     */
    public static Browser getBrowser(String agentInfo){
        if(StringUtils.isBlank(agentInfo)){
            throw new IllegalArgumentException("客户端信息不能为空");
        }

        return getUserAgent(agentInfo).getBrowser();
    }

    /**
     * 获取客户端系统信息
     */
    public static OperatingSystem getOperatingSystem(String agentInfo){
        if(StringUtils.isBlank(agentInfo)){
            throw new IllegalArgumentException("客户端信息不能为空");
        }

        return UserAgent.parseUserAgentString(agentInfo).getOperatingSystem();
    }

    /**
     * 判断客户端浏览器是否IE
     */
    public static boolean isIE(String agentInfo){
        Browser browser = getBrowser(agentInfo);
        Manufacturer manufacturer = browser.getManufacturer();

        return manufacturer.equals(Manufacturer.MICROSOFT);
    }

    /**
     * 判断客户端系统是否WEB设备
     */
    public static boolean isWeb(String agentInfo){
        Browser browser = getBrowser(agentInfo);
        BrowserType type = browser.getBrowserType();

        return type.equals(BrowserType.WEB_BROWSER);
    }
}
