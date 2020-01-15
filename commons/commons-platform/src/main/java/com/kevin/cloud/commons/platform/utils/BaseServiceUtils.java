package com.kevin.cloud.commons.platform.utils;

import com.github.pagehelper.PageInfo;
import com.kevin.cloud.commons.platform.dto.PageResult;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 平台 service 服务模块统一工具类
 * @author: kevin
 * @create: 2020-01-14 12:19
 **/
public class BaseServiceUtils {

    public static  PageResult buildPageResult(PageInfo pageInfo){
      return   PageResult.builder().pages(pageInfo.getPages())
              .list(pageInfo.getList())
              .pageNum(pageInfo.getPageNum())
              .pageSize(pageInfo.getPageSize())
              .total(pageInfo.getTotal()).build();
    }

}
