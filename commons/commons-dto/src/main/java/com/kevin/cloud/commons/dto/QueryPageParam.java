package com.kevin.cloud.commons.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 分页请求参数
 * @author: kevin
 * @create: 2020-01-12 22:56
 **/
@Data
public class QueryPageParam  {
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
