package com.rab.babylon.commons.security.mybatis;

import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.utils.NameUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.List;

public class Criterion{

    //运算符
    private final static String LIKE = "LIKE";
    private final static String NOT_LIKE = "NOT LIKE";
    private final static String IS_NULL = "IS NULL";
    private final static String IS_NOT_NULL = "IS NOT NULL";
    private final static String IN = "IN";
    private final static String NOT_IN = "NOT IN";
    private final static String EQUAL = "=";
    private final static String NOT_EQUAL = "<>";
    private final static String LESS_EQUAL = "<=";
    private final static String GREATER_EQUAL = ">=";
    private final static String LESS_THAN = "<";
    private final static String GREATER_THAN = ">";
    private final static String BETWEEN = "BETWEEN";
    private final static String NOT_BETWEEN = "NOT BETWEEN";
    private final static String DESC = "DESC";
    private final static String ASC = "ASC";
    private final static String LIMIT = "LIMIT";
    private final static String GROUP_BY = "GROUP BY";
    private final static String OR = "OR";
    private final static String AND = "AND";

    //属性名
    private String property;
    //运算符
    private String operator;
    //值
    private Object value;
    //第二值
    private Object secondValue;
    //无值标识
    private boolean noValue;
    //单值标识
    private boolean singleValue;
    //两值标识
    private boolean betweenValue;
    //多值标识
    private boolean listValue;
    //Criterion与标识
    private boolean andCriterionValue;
    //Criterion或标识
    private boolean orCriterionValue;

    public String getProperty(){
        return property;
    }

    public String getOperator(){
        return operator;
    }

    public Object getValue(){
        return value;
    }

    public Object getSecondValue(){
        return secondValue;
    }

    public boolean getNoValue(){
        return noValue;
    }

    public boolean getSingleValue(){
        return singleValue;
    }

    public boolean getBetweenValue(){
        return betweenValue;
    }

    public boolean getListValue(){
        return listValue;
    }

    public boolean getAndCriterionValue(){
        return andCriterionValue;
    }

    public boolean getOrCriterionValue(){
        return orCriterionValue;
    }

    public void setValue(Object value){
        if(value == null){
            throw new DataAccessException("运算值不能为空");
        }

        if(value instanceof List && ((List) value).isEmpty()){
            throw new DataAccessException("运算数组不能为空");
        }

        this.value = value;
    }

    public void setSecondValue(Object value){
        if(value == null){
            throw new DataAccessException("运算值不能为空");
        }

        if(value instanceof List && ((List) value).isEmpty()){
            throw new DataAccessException("运算数组不能为空");
        }

        this.secondValue = value;
    }

    public void setProperty(String property){
        if(StringUtils.isBlank(property)){
            throw new DataAccessException("属性名称不能为空");
        }

        this.property = NameUtils.toUnderLine(property);
    }

    /**
     * 模糊
     */
    public void like(String property, String value){
        setValue(MessageFormat.format("%{0}%", value));
        setProperty(property);

        this.operator = LIKE;
        this.singleValue = true;
    }

    /**
     * 模糊不相似
     */
    public void notLike(String property, String value){
        setValue(MessageFormat.format("%{0}%", value));
        setProperty(property);

        this.operator = NOT_LIKE;
        this.singleValue = true;
    }

    /**
     * 相等
     */
    public void eq(String property, Object value){
        setValue(value);
        setProperty(property);

        this.operator = EQUAL;
        this.singleValue = true;
    }

    /**
     * 不相等
     */
    public void notEq(String property, Object value){
        setValue(value);
        setProperty(property);

        this.operator = NOT_EQUAL;
        this.singleValue = true;
    }

    /**
     * 小于等于
     */
    public void le(String property, Object value){
        setValue(value);
        setProperty(property);

        this.operator = LESS_EQUAL;
        this.singleValue = true;
    }

    /**
     * 小于
     */
    public void lt(String property, Object value){
        setValue(value);
        setProperty(property);

        this.operator = LESS_THAN;
        this.singleValue = true;
    }

    /**
     * 大于等于
     */
    public void ge(String property, Object value){
        setValue(value);
        setProperty(property);

        this.operator = GREATER_EQUAL;
        this.singleValue = true;
    }

    /**
     * 大于
     */
    public void gt(String property, Object value){
        setValue(value);
        setProperty(property);

        this.operator = GREATER_THAN;
        this.singleValue = true;
    }

    /**
     * 之间
     */
    public void between(String property, Object min, Object max){
        setValue(min);
        setSecondValue(max);
        setProperty(property);

        this.operator = BETWEEN;
        this.betweenValue = true;
    }

    /**
     * 之外
     */
    public void notBetween(String property, Object min, Object max){
        setValue(min);
        setSecondValue(max);
        setProperty(property);

        this.operator = NOT_BETWEEN;
        this.betweenValue = true;
    }

    /**
     * 其中
     */
    public void in(String property, List value){
        setValue(value);
        setProperty(property);

        this.operator = IN;
        this.listValue = true;
    }

    /**
     * 其外
     */
    public void notIn(String property, List value){
        setValue(value);
        setProperty(property);

        this.operator = NOT_IN;
        this.listValue = true;
    }

    /**
     * 空
     */
    public void isNull(String property){
        setProperty(property);

        this.operator = IS_NULL;
        this.noValue = true;
    }

    /**
     * 非空
     */
    public void isNotNull(String property){
        setProperty(property);

        this.operator = IS_NOT_NULL;
        this.noValue = true;
    }

    /**
     * 空字符
     */
    public void isBlank(String property){
        setProperty(property);
        setValue("");

        this.operator = EQUAL;
        this.singleValue = true;
    }

    /**
     * 非空字符串
     */
    public void isNotBlank(String property){
        setProperty(property);
        setValue("");

        this.operator = NOT_EQUAL;
        this.singleValue = true;
    }

    /**
     * 或
     */
    public void or(List<Criterion> value){
        setValue(value);

        this.operator = OR;
        this.orCriterionValue = true;
    }

    /**
     * 与
     */
    public void and(List<Criterion> value){
        setValue(value);

        this.operator = AND;
        this.andCriterionValue = true;
    }

    /**
     * 降序
     */
    public void desc(String property){
        setProperty(property);

        this.operator = DESC;
        this.noValue = true;
    }

    /**
     * 升序
     */
    public void asc(String property){
        setProperty(property);

        this.operator = ASC;
        this.noValue = true;
    }

    /**
     * 分页
     */
    public void limit(Long offset, Long size){
        if(offset == null || offset < 0){
            throw new DataAccessException("分页游标不能为空或小于0");
        }

        if(size == null || size < 0){
            throw new DataAccessException("分页记录数量不能为空或小于0");
        }

        setValue(offset);
        setSecondValue(size);

        this.operator = LIMIT;
        this.betweenValue = true;
    }

    /**
     * 分组
     */
    public void groupBy(String property){
        setProperty(property);

        this.operator = GROUP_BY;
        this.noValue = true;
    }
}
