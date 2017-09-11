package com.rab.core.account.entity;

import com.rab.core.base.entity.Create;
import com.rab.core.base.entity.Delete;
import com.rab.core.base.entity.Update;
import com.rab.core.consts.entity.UsingState;

import java.util.Date;

/**
 * 系统账户
 */
public class Account implements Create, Update, Delete{

    //ID
    private String id;
    //登录名称
    private String loginname;
    //手机号码
    private String phone;
    //电子邮箱
    private String email;
    //登录密码
    private String password;
    //账户状态
    private UsingState state;
    //是否删除
    private Boolean isDelete;
    //创建时间
    private Date CreateTime;
    //更新时间
    private Date updateTime;
    //删除时间
    private Date deleteTime;

    public Account(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getLoginname(){
        return loginname;
    }

    public void setLoginname(String loginname){
        this.loginname = loginname;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    @Override
    public Boolean getIsDelete(){
        return isDelete;
    }

    @Override
    public void setIsDelete(Boolean isDelete){
        this.isDelete = isDelete;
    }

    @Override
    public Date getCreateTime(){
        return CreateTime;
    }

    @Override
    public void setCreateTime(Date createTime){
        CreateTime = createTime;
    }

    @Override
    public Date getUpdateTime(){
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    @Override
    public Date getDeleteTime(){
        return deleteTime;
    }

    @Override
    public void setDeleteTime(Date deleteTime){
        this.deleteTime = deleteTime;
    }
}
