package com.kevin.cloud.commons.platform.service.fallback;


import com.kevin.cloud.commons.platform.dto.FallBackResult;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-11 20:48
 **/
public abstract class BaseFallBack implements IBaseFallBack {
    private static final int errorLength = 230;

    public static FallBackResult fallBackError(FallBackResult fallBackResult, Throwable throwable) {
        FallBackResult fallBackResultEnd = null;
        if (fallBackResult == null) {
            fallBackResultEnd = new FallBackResult();
        }
        fallBackResultEnd.setStatus(false);
        fallBackResultEnd.setFallbackReason(throwable.toString().substring(0, errorLength));
        return fallBackResultEnd;
    }

    public static FallBackResult fallBackError(Throwable throwable) {
        return fallBackError(null, throwable);
    }
}
