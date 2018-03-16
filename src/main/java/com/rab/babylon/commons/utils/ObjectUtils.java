package com.rab.babylon.commons.utils;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

/**
 * 序列化/反序列化工具
 */
public class ObjectUtils{

    private static Gson gson;

    private ObjectUtils(){

    }

    private static Gson getGson(){
        if(gson == null){
            gson = GsonBuilderUtils.getGson();
        }

        return gson;
    }

    /**
     * 序列化
     */
    public static String serialize(Object object){
        if(object == null){
            return null;
        }

        return getGson().toJson(object);
    }

    /**
     * 序列化
     */
    public static String toString(Object object){
        if(object == null){
            return "";
        }

        if(object instanceof String || object instanceof Number){
            return object.toString();
        }

        return getGson().toJson(object).replaceAll("\"|\\{|\\}|\\[|\\]", "");
    }

    /**
     * 反序列化
     */
    public static <T> T deserialize(String json, Class<T> clazz){
        if(StringUtils.isBlank(json)){
            return null;
        }

        if(clazz == null){
            return null;
        }

        return getGson().fromJson(json, clazz);
    }

    /**
     * 反序列化
     */
    public static <T> T deserialize(String json, Type type){
        if(StringUtils.isBlank(json)){
            return null;
        }

        if(type == null){
            return null;
        }

        return getGson().fromJson(json, type);
    }
}
