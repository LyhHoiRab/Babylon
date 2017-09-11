package com.rab.core.base.entity;

import java.util.Date;

/**
 * 实体类基础父接口
 * 实现该接口表示实体在数据库中记录可以被创建
 */
public interface Create{

    void setCreateTime(Date createTime);

    Date getCreateTime();
}
