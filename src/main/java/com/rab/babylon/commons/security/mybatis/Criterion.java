package com.rab.babylon.commons.security.mybatis;

import com.rab.babylon.commons.utils.NameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Criterion{

    //通配符
    private final static String WILDCARD = "%";

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
    //多Criterion标识
    private boolean criterionValue;

    public String getProperty(){
        return property;
    }

    public void setProperty(String property){
        this.property = property;
    }

    public String getOperator(){
        return operator;
    }

    public void setOperator(String operator){
        this.operator = operator;
    }

    public Object getValue(){
        return value;
    }

    public void setValue(Object value){
        this.value = value;
    }

    public Object getSecondValue(){
        return secondValue;
    }

    public void setSecondValue(Object secondValue){
        this.secondValue = secondValue;
    }

    public boolean getNoValue(){
        return noValue;
    }

    public void setNoValue(boolean noValue){
        this.noValue = noValue;
    }

    public boolean getSingleValue(){
        return singleValue;
    }

    public void setSingleValue(boolean singleValue){
        this.singleValue = singleValue;
    }

    public boolean getBetweenValue(){
        return betweenValue;
    }

    public void setBetweenValue(boolean betweenValue){
        this.betweenValue = betweenValue;
    }

    public boolean getListValue(){
        return listValue;
    }

    public void setListValue(boolean listValue){
        this.listValue = listValue;
    }

    private void checkValue(Object value){
        if(value == null){
            throw new IllegalArgumentException("运算值不能为空");
        }

        if(value instanceof List<?> && ((List) value).isEmpty()){
            throw new IllegalArgumentException("运算数组不能为空");
        }

        if(value instanceof String && StringUtils.isBlank((String) value)){
            throw new IllegalArgumentException("运算字符串不能为空");
        }
    }

    private String checkProperty(String property){
        if(StringUtils.isBlank(property)){
            throw new IllegalArgumentException("属性名不能为空");
        }

        return NameUtils.toUnderLine(property);
    }

    /**
     * 模糊查询
     */
    public void like(String property, String value){
        checkValue(value);
        property = checkProperty(property);

        this.property = WILDCARD + property + WILDCARD;
        this.value = value;
        this.operator = LIKE;
        this.singleValue = true;
    }

    /**
     * 模糊不相似
     */
    public void notLike(String property, String value){
        checkValue(value);
        property = checkProperty(property);

        this.property = WILDCARD + property + WILDCARD;
        this.value = value;
        this.operator = NOT_LIKE;
        this.singleValue = true;
    }

    /**
     * 相等
     */
    public void eq(String property, Object value){
        checkValue(value);
        property = checkProperty(property);

        this.property = property;
        this.value = value;
        this.operator = EQUAL;
        this.singleValue = true;
    }

    /**
     * 不相等
     */
    public void notEq(String property, Object value){
        checkValue(value);
        property = checkProperty(property);

        this.property = property;
        this.value = value;
        this.operator = NOT_EQUAL;
        this.singleValue = true;
    }

    /**
     * 不大于
     */
    public void le(String property, Object value){
        checkValue(value);
        property = checkProperty(property);

        this.property = property;
        this.value = value;
        this.operator = LESS_EQUAL;
        this.singleValue = true;
    }

    /**
     * 小于
     */
    public void lt(String property, Object value){
        checkValue(value);
        property = checkProperty(property);

        this.property = property;
        this.value = value;
        this.operator = LESS_THAN;
        this.singleValue = true;
    }

    /**
     * 不小于
     */
    public void ge(String property, Object value){
        checkValue(value);
        property = checkProperty(property);

        this.property = property;
        this.value = value;
        this.operator = GREATER_EQUAL;
        this.singleValue = true;
    }

    /**
     * 大于
     */
    public void gt(String property, Object value){
        checkValue(value);
        property = checkProperty(property);

        this.property = property;
        this.value = value;
        this.operator = GREATER_THAN;
        this.singleValue = true;
    }

    /**
     * 之间
     */
    public void between(String property, Object min, Object max){
        checkValue(min);
        checkValue(max);
        property = checkProperty(property);

        this.property = property;
        this.value = min;
        this.secondValue = max;
        this.operator = BETWEEN;
        this.betweenValue = true;
    }

    /**
     * 之外
     */
    public void notBetween(String property, Object min, Object max){
        checkValue(min);
        checkValue(max);
        property = checkProperty(property);

        this.property = property;
        this.value = min;
        this.secondValue = max;
        this.operator = NOT_BETWEEN;
        this.betweenValue = true;
    }

    /**
     * 在其中
     */
    public void in(String property, List<?> value){
        checkValue(value);
        property = checkProperty(property);

        this.property = property;
        this.value = value;
        this.operator = IN;
        this.listValue = true;
    }

    /**
     * 在其外
     */
    public void notIn(String property, List<?> value){
        checkValue(value);
        property = checkProperty(property);

        this.property = property;
        this.value = value;
        this.operator = NOT_IN;
        this.listValue = true;
    }

    /**
     * 为空
     */
    public void isNull(String property){
        property = checkProperty(property);

        this.property = property;
        this.operator = IS_NULL;
        this.noValue = true;
    }

    /**
     * 多条件或
     */
    public void or(List<Criterion> criterions){
        if(criterions == null || criterions.size() <= 1){
            throw new IllegalArgumentException("多条件个数不能少于1");
        }

        this.operator = OR;
        this.value = criterions;
        this.criterionValue = true;
    }

    /**
     * 多条件与
     */
    public void and(List<Criterion> criterions){
        if(criterions == null || criterions.size() <= 1){
            throw new IllegalArgumentException("多条件个数不能少于1");
        }

        this.operator = AND;
        this.value = criterions;
        this.criterionValue = true;
    }

    /**
     * 不为空
     */
    public void notNull(String property){
        property = checkProperty(property);

        this.property = property;
        this.operator = IS_NOT_NULL;
        this.noValue = true;
    }

    /**
     * 倒序排序
     */
    public void desc(String property){
        property = checkProperty(property);

        this.property = property;
        this.operator = DESC;
        this.noValue = true;
    }

    /**
     * 顺序排序
     */
    public void asc(String property){
        property = checkProperty(property);

        this.property = property;
        this.operator = ASC;
        this.noValue = true;
    }

    /**
     * 分页
     */
    public void limit(Long offset, Long size){
        if(offset == null || offset < 0 || size == null || size < 0){
            throw new IllegalArgumentException("分页参数不能为空或少于0");
        }

        this.value = offset;
        this.secondValue = size;
        this.operator = LIMIT;
        this.betweenValue = true;
    }
}
