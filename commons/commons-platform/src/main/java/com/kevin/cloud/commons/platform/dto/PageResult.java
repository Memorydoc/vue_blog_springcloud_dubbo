package com.kevin.cloud.commons.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 分页返回Dto
 * @author: kevin
 * @create: 2020-01-14 11:28
 **/
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 4229644059253693353L;
    private long total;        //总记录数
    private List<T> list;    //结果集
    private int pageNum;    // 第几页
    private int pageSize;    // 每页记录数
    private int pages;        // 总页数
    private int size;        // 就是返回的数据条数，
    /**
     * 本页的数据列表 搜索引擎分页会用到
     */
    private List<Map<String, Object>> recordList;


    public PageResult(int pageNum, int pageSize, long total, List<Map<String, Object>> recordList) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.recordList = recordList;
    }
    public PageResult(int pageNum, int pageSize, long total, List<Map<String, Object>> recordList, int size) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.recordList = recordList;
        this.size = size;
    }
}
