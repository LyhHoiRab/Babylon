package com.rab.commons.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

/**
 * gson创建工具
 */
public class GsonBuilderUtils{

    private static GsonBuilder builder;

    private GsonBuilderUtils(){

    }

    /**
     * Long反序列Date
     */
    private static class DateDeserializer implements JsonDeserializer<Date>{

        @Override
        public Date deserialize(JsonElement element, Type typeOf, JsonDeserializationContext context) throws JsonParseException{
            return new Date(element.getAsJsonPrimitive().getAsLong());
        }
    }

    /**
     * Date序列化Long
     */
    private static class DateSerializer implements JsonSerializer<Date>{

        @Override
        public JsonElement serialize(Date date, Type typeOf, JsonSerializationContext context){
            return new JsonPrimitive(date.getTime());
        }
    }

    /**
     * 获取GsonBuilder
     */
    private static GsonBuilder getBuilder(){
        if(builder == null){
            builder = new GsonBuilder().serializeNulls()
                    .registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                    .registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
        }

        return builder;
    }

    /**
     * 获取gson
     */
    public static Gson getGson(){
        return getBuilder().create();
    }
}
