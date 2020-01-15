package com.kevin.cloud.provider.service.fallback;

import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.QueryPageParam;
import com.kevin.cloud.commons.platform.service.fallback.BaseFallBack;

/**
 * @program: vue-blog-backend
 * @description: 文章熔断降级dubbo服务
 * @author: kevin
 * @create: 2020-01-15 14:36
 **/
public class ArticleServiceDubboFallBack extends BaseFallBack {
    public static FallBackResult articleListFallBack (QueryPageParam queryPageParam, Throwable ex) {
        return fallBackError(ex);
    }
}
