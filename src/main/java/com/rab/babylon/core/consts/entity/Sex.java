package com.rab.babylon.core.consts.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 性别枚举
 */
public enum Sex{

    @SerializedName("0")
    FEMALE(0, "女性"),

    @SerializedName("1")
    MALE(1, "男性"),

    @SerializedName("2")
    UNKNOWN(2, "未知"),

    @SerializedName("3")
    NEUTRAL(3, "中性"),

    @SerializedName("4")
    MANLY(4, "偏男性"),

    @SerializedName("5")
    WOMANLY(5, "偏女性");

    private int id;
    private String description;

    private Sex(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public static Sex getById(int id){
        for(Sex sex : Sex.values()){
            if(sex.getId() == id){
                return sex;
            }
        }

        return UNKNOWN;
    }
}
