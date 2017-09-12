package com.rab.babylon.commons.security.mybatis;

import com.rab.babylon.commons.utils.NameUtils;
import org.apache.commons.lang3.StringUtils;

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

    //值
    private Object[] value;
    //运算符
    private String operator;
    //属性名成
    private String property;

    public Object[] getValue(){
        return value;
    }

    public void setValue(Object[] value){
        this.value = value;
    }

    public String getOperator(){
        return operator;
    }

    public void setOperator(String operator){
        this.operator = operator;
    }

    public String getProperty(){
        return property;
    }

    public void setProperty(String property){
        this.property = property;
    }

    public void checkProperty(String property){
        if(StringUtils.isBlank(property)){
            throw new IllegalArgumentException("查询属性名不能为空");
        }
    }

    public void checkValues(Object... values){
        if(values == null || values.length < 1){
            throw new IllegalArgumentException("查询的属性值不能为空");
        }

        for(Object value : values){
            if(value == null){
                throw new IllegalArgumentException("查询的属性值不能为空");
            }
        }
    }

    /**
     * 模糊查询
     */
    public void like(String property, String value){
        checkProperty(property);
        checkValues(value);

        value = WILDCARD + value + WILDCARD;

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{value};
        this.operator = LIKE;
    }

    /**
     * 模糊不相似
     */
    public void notLike(String property, String value){
        checkProperty(property);
        checkValues(value);

        value = WILDCARD + value + WILDCARD;

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{value};
        this.operator = NOT_LIKE;
    }

    /**
     * 相等
     */
    public void eq(String property, Object value){
        checkProperty(property);
        checkValues(value);

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{value};
        this.operator = EQUAL;
    }

    /**
     * 不相等
     */
    public void notEq(String property, Object value){
        checkProperty(property);
        checkValues(value);

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{value};
        this.operator = NOT_EQUAL;
    }

    /**
     * 不大于
     */
    public void le(String property, Object value){
        checkProperty(property);
        checkValues(value);

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{value};
        this.operator = LESS_EQUAL;
    }

    /**
     * 小于
     */
    public void lt(String property, Object value){
        checkProperty(property);
        checkValues(value);

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{value};
        this.operator = LESS_THAN;
    }

    /**
     * 不小于
     */
    public void ge(String property, Object value){
        checkProperty(property);
        checkValues(value);

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{value};
        this.operator = GREATER_EQUAL;
    }

    /**
     * 大于
     */
    public void gt(String property, Object value){
        checkProperty(property);
        checkValues(value);

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{value};
        this.operator = GREATER_THAN;
    }

    /**
     * 之间
     */
    public void between(String property, Object min, Object max){
        checkProperty(property);
        checkValues(min, max);

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{min, max};
        this.operator = BETWEEN;
    }

    /**
     * 之外
     */
    public void notBetween(String property, Object min, Object max){
        checkProperty(property);
        checkValues(min, max);

        this.property = NameUtils.toUnderLine(property);
        this.value = new Object[]{min, max};
        this.operator = NOT_BETWEEN;
    }

    /**
     * 在其中
     */
    public void in(String property, Object... values){
        checkProperty(property);
        checkValues(values);

        this.property = NameUtils.toUnderLine(property);
        this.value = values;
        this.operator = IN;
    }

    /**
     * 在其外
     */
    public void notIn(String property, Object... values){
        checkProperty(property);
        checkValues(values);

        this.property = NameUtils.toUnderLine(property);
        this.value = values;
        this.operator = NOT_IN;
    }

    /**
     * 为空
     */
    public void isNull(String property){
        checkProperty(property);

        this.property = NameUtils.toUnderLine(property);
        this.operator = IS_NULL;
    }

    /**
     * 不为空
     */
    public void notNull(String property){
        checkProperty(property);

        this.property = NameUtils.toUnderLine(property);
        this.operator = IS_NOT_NULL;
    }

    /**
     * 倒序排序
     */
    public void desc(String property){
        checkProperty(property);

        this.property = NameUtils.toUnderLine(property);
        this.operator = DESC;
    }

    /**
     * 顺序排序
     */
    public void asc(String property){
        checkProperty(property);

        this.property = NameUtils.toUnderLine(property);
        this.operator = ASC;
    }

    /**
     * 分页
     */
    public void limit(Long offset, Long size){
        checkValues(offset, size);

        this.value = new Object[]{offset, size};
    }
}
