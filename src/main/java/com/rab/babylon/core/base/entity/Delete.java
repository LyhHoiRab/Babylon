package com.rab.babylon.core.base.entity;

import java.util.Date;

/**
 * 实体类基础父接口
 * 实现该接口表示实体在数据库中记录只能逻辑删除
 */
public interface Delete{

    void setIsDelete(Boolean isDelete);

    Boolean getIsDelete();

    void setDeleteTime(Date deleteTime);

    Date getDeleteTime();
}
