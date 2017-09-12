package com.rab.babylon.core.consts.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 资源使用状态
 */
public enum UsingState{

    @SerializedName("0")
    NORMAL(0, "正常"),

    @SerializedName("1")
    INACTIVE(1, "未激活"),

    @SerializedName("2")
    LOCKED(2, "锁定"),

    @SerializedName("3")
    FROZEN(3, "冻结"),

    @SerializedName("4")
    UNAVAILABLE(4, "不可用");

    private int id;
    private String description;

    private UsingState(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public static UsingState getById(int id){
        for(UsingState state : UsingState.values()){
            if(state.getId() == id){
                return state;
            }
        }

        throw new IllegalArgumentException("未知的使用状态常量");
    }
}
