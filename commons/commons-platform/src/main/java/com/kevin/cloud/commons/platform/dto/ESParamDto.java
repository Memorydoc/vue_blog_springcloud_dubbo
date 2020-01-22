package com.kevin.cloud.commons.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: vue-blog-backend
 * @description: ES 统一参数
 * @author: kevin
 * @create: 2020-01-22 20:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESParamDto {
    private String index;// 索引库名
    private String type; // 表名，匹配 type
    private String keyword; // 查询关键字
    private String matchField; //  搜索匹配关键字的字段
    private boolean isPage; // 是否分页显示
    private boolean isHightLight; // 是否高亮字段
    private Object queryBuilder; // 查询构造build
    private int pageNum = 0; // 第几页， 注意 在es中 pageNum 是从第0页开始
    private int pageSize;  // 每页数据
    //        需要显示的字段，逗号分隔（缺省为全部字段）
    //       排序字段
    private String fields;
    private String sortField;
    private String hightLightField; // 需要高亮的字段 只有在 isHightLight == true时 才会用到
    private int documentSize;// 查询的文档大小限制 默认不限制

}
