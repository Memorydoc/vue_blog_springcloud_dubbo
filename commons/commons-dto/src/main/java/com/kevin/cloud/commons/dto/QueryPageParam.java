package com.kevin.cloud.commons.dto;

import java.io.Serializable;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 分页请求参数
 * @author: kevin
 * @create: 2020-01-12 22:56
 **/
public class QueryPageParam implements Serializable {

    private static final long serialVersionUID = 4021449332228999455L;
    private Integer pageNum;//第几页
    private Integer pageSize; //每页的数量

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public String toString() {
        return "QueryPageParam{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
