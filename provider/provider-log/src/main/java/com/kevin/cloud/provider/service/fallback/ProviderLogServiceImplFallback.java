package com.kevin.cloud.provider.service.fallback;

import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.service.fallback.BaseFallBack;
import com.kevin.cloud.provider.domain.UmsAdminLoginLog;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-12 23:08
 **/
public class ProviderLogServiceImplFallback extends BaseFallBack {

    public static FallBackResult queryUserLoginLogByPageFallBack(UmsAdminLoginLog umsAdminLoginLog, Throwable ex) {
        return fallBackError(ex);
    }


    public static FallBackResult sendUmsAdminLoginLogFallBack(UmsAdminLoginLog umsAdminLoginLog, Throwable ex) {
        return fallBackError(ex);
    }
}
