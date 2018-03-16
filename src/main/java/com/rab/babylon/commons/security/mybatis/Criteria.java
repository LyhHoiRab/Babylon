package com.rab.babylon.commons.security.mybatis;

import java.util.ArrayList;
import java.util.List;

public class Criteria{

    //与
    private List<Criterion> andCriterions;
    //或
    private List<Criterion> orCriterions;
    //排序
    private List<Criterion> sorts;
    //分组
    private List<Criterion> groups;
    //分页
    private Criterion limit;

    public Criteria(){
        this.andCriterions = new ArrayList<Criterion>();
        this.orCriterions = new ArrayList<Criterion>();
        this.sorts = new ArrayList<Criterion>();
        this.groups = new ArrayList<Criterion>();
    }

    public List<Criterion> getAndCriterions(){
        return andCriterions;
    }

    public List<Criterion> getOrCriterions(){
        return orCriterions;
    }

    public List<Criterion> getSorts(){
        return sorts;
    }

    public List<Criterion> getGroups(){
        return groups;
    }

    public Criterion getLimit(){
        return limit;
    }

    public Criteria and(Criterion criterion){
        andCriterions.add(criterion);
        return this;
    }

    public Criteria or(Criterion criterion){
        orCriterions.add(criterion);
        return this;
    }

    public Criteria sort(Criterion criterion){
        sorts.add(criterion);
        return this;
    }

    public Criteria groupBy(Criterion criterion){
        groups.add(criterion);
        return this;
    }

    public Criteria limit(Criterion criterion){
        limit = criterion;
        return this;
    }

    public void clear(){
        andCriterions.clear();
        orCriterions.clear();
        sorts.clear();
        groups.clear();
        limit = null;
    }
}
