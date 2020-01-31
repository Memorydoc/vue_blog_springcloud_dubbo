package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.article.dto.SiFinkDto;

import java.util.List;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.api
 * @ClassName: SiFinkService
 * @Author: kevin
 * @Description:
 * @Date: 2020/1/31 23:08
 * @Version: 1.0
 */
public interface SiFinkService {

    public List<SiFinkDto> initLinks();
}
