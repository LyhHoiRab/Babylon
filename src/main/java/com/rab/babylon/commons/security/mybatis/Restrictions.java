package com.rab.babylon.commons.security.mybatis;

import java.util.Arrays;
import java.util.List;

public class Restrictions{

    public static Criterion like(String property, String value){
        Criterion criterion = new Criterion();
        criterion.like(property, value);

        return criterion;
    }

    public static Criterion notLike(String property, String value){
        Criterion criterion = new Criterion();
        criterion.notLike(property, value);

        return criterion;
    }

    public static Criterion eq(String property, Object value){
        Criterion criterion = new Criterion();
        criterion.eq(property, value);

        return criterion;
    }

    public static Criterion notEq(String property, Object value){
        Criterion criterion = new Criterion();
        criterion.notEq(property, value);

        return criterion;
    }

    public static Criterion le(String property, Object value){
        Criterion criterion = new Criterion();
        criterion.le(property, value);

        return criterion;
    }

    public static Criterion lt(String property, Object value){
        Criterion criterion = new Criterion();
        criterion.lt(property, value);

        return criterion;
    }

    public static Criterion ge(String property, Object value){
        Criterion criterion = new Criterion();
        criterion.ge(property, value);

        return criterion;
    }

    public static Criterion gt(String property, Object value){
        Criterion criterion = new Criterion();
        criterion.gt(property, value);

        return criterion;
    }

    public static Criterion between(String property, Object min, Object max){
        Criterion criterion = new Criterion();
        criterion.between(property, min, max);

        return criterion;
    }

    public static Criterion in(String property, List value){
        Criterion criterion = new Criterion();
        criterion.in(property, value);

        return criterion;
    }

    public static Criterion notIn(String property, List value){
        Criterion criterion = new Criterion();
        criterion.notIn(property, value);

        return criterion;
    }

    public static Criterion isNull(String property){
        Criterion criterion = new Criterion();
        criterion.isNull(property);

        return criterion;
    }

    public static Criterion isNotNull(String property){
        Criterion criterion = new Criterion();
        criterion.isNotNull(property);

        return criterion;
    }

    public static Criterion isBlank(String property){
        Criterion criterion = new Criterion();
        criterion.isBlank(property);

        return criterion;
    }

    public static Criterion isNotBlank(String property){
        Criterion criterion = new Criterion();
        criterion.isNotBlank(property);

        return criterion;
    }

    public static Criterion or(Criterion... value){
        Criterion criterion = new Criterion();
        criterion.or(Arrays.asList(value));

        return criterion;
    }

    public static Criterion and(Criterion... value){
        Criterion criterion = new Criterion();
        criterion.and(Arrays.asList(value));

        return criterion;
    }

    public static Criterion desc(String property){
        Criterion criterion = new Criterion();
        criterion.desc(property);

        return criterion;
    }

    public static Criterion asc(String property){
        Criterion criterion = new Criterion();
        criterion.asc(property);

        return criterion;
    }

    public static Criterion limit(Long offset, Long size){
        Criterion criterion = new Criterion();
        criterion.limit(offset, size);

        return criterion;
    }

    public static Criterion groupBy(String property){
        Criterion criterion = new Criterion();
        criterion.groupBy(property);

        return criterion;
    }
}
