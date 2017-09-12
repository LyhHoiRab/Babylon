package com.rab.babylon.commons.security.converter;

import com.google.gson.Gson;
import com.rab.babylon.commons.utils.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * JSON输出转换器
 */
public class RsonHttpMessageConverter extends GsonHttpMessageConverter{

    private static Gson gson;

    public RsonHttpMessageConverter(){
        super.setGson(getGson());
    }

    @Override
    public Gson getGson(){
        if(gson == null){
            gson = GsonBuilderUtils.getGson();
        }

        return gson;
    }
}
