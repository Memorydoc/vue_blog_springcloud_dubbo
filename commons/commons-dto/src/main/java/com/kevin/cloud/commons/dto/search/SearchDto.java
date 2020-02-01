package com.kevin.cloud.commons.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-18 15:54
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private List<Map<Object, Object>> data;// 搜索出的数据组装之后的
    private Long count; //搜出总记录数
    private Long cast;//耗时
}
