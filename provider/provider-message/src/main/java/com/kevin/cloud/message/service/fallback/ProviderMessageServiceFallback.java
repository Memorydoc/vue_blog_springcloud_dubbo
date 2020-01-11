package com.kevin.cloud.message.service.fallback;

import com.kevin.cloud.message.domain.UmsAdminLoginLog;
import com.kevin.cloud.platform.dto.FallBackResult;
import com.kevin.cloud.platform.service.fallback.BaseFallBack;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 消息服务熔断器
 * @author: kevin
 * @create: 2020-01-11 13:56
 **/
public class ProviderMessageServiceFallback  extends BaseFallBack {

    public static FallBackResult sendUmsAdminLoginLogFallBack(UmsAdminLoginLog umsAdminLoginLog, Throwable ex) {
        return fallBackError(ex);
    }

}
