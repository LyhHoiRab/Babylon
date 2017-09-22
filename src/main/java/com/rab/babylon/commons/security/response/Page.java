package com.rab.babylon.commons.security.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果
 */
public class Page<T>{

    //总条数
    private Long total;
    //分页参数
    private PageRequest pageRequest;
    //分页数据
    private List<T> content = new ArrayList<T>();

    public Page(List<T> content, PageRequest pageRequest, Long total){
        this.pageRequest = pageRequest;
        this.content.addAll(content);
        this.total = (!content.isEmpty() && pageRequest != null && (pageRequest.getOffset() + pageRequest.getPageSize()) > total) ? pageRequest.getOffset() + content.size() : total;
    }

    public Long getTotal(){
        return total;
    }

    public void setTotal(Long total){
        this.total = total;
    }

    public PageRequest getPageRequest(){
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest){
        this.pageRequest = pageRequest;
    }

    public List<T> getContent(){
        return content;
    }

    public void setContent(List<T> content){
        this.content = content;
    }
}
