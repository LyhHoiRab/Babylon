package com.rab.babylon.commons.utils;

import java.util.UUID;

public class UUIDGenerator{

    private UUIDGenerator(){

    }

    //生成32位UUID
    public static String by32(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //生成36位UUID
    public static String by36(){
        return UUID.randomUUID().toString();
    }
}
