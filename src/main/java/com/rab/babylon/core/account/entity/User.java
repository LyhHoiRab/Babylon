package com.rab.babylon.core.account.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Delete;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.Sex;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.List;

/**
 * 系统用户
 */
public class User implements Create, Update, Delete{

    //ID
    private String id;
    //账户ID
    private String accountId;
    //用户编码
    private Integer code;
    //微信号
    private String wechat;
    //用户昵称
    private String nickname;
    //真实姓名
    private String name;
    //身份证
    private String idCard;
    //性别
    private Sex sex;
    //出生日期
    private Date birthday;
    //手机
    private String phone;
    //职业
    private String position;
    //用户状态
    private UsingState state;
    //头像
    private String headImgUrl;
    //用户扩展属性
    private List<UserExpand> expands;
    //是否删除
    private Boolean isDelete;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除时间
    private Date deleteTime;
    //能否观看课程
    private Boolean hasRole;

    public User(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getAccountId(){
        return accountId;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getIdCard(){
        return idCard;
    }

    public void setIdCard(String idCard){
        this.idCard = idCard;
    }

    public Sex getSex(){
        return sex;
    }

    public void setSex(Sex sex){
        this.sex = sex;
    }

    public Date getBirthday(){
        return birthday;
    }

    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    public List<UserExpand> getExpands(){
        return expands;
    }

    public void setExpands(List<UserExpand> expands){
        this.expands = expands;
    }

    public String getHeadImgUrl(){
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl){
        this.headImgUrl = headImgUrl;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public Integer getCode(){
        return code;
    }

    public Boolean getHasRole(){
        return hasRole;
    }

    public void setHasRole(Boolean hasRole){
        this.hasRole = hasRole;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public String getWechat(){
        return wechat;
    }

    public void setWechat(String wechat){
        this.wechat = wechat;
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
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
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
