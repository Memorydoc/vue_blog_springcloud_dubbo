package com.kevin.cloud.provider.service.fallback;

import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.service.fallback.BaseFallBack;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.service.fallback
 * @ClassName: RedisTemplateServiceFallback
 * @Author: kevin
 * @Description:  redisTempalte 异常处理
 * @Date: 2020/2/14 15:22
 * @Version: 1.0
 */
public class RedisTemplateServiceFallback extends BaseFallBack  {


    public static FallBackResult setFallback (QueryPageParam queryPageParam, Throwable ex) {
        return fallBackError(ex);
    }
}
