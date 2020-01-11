package com.kevin.cloud.message.service.fallback;

import com.kevin.cloud.message.domain.UmsAdminLoginLog;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 消息服务熔断器
 * @author: kevin
 * @create: 2020-01-11 13:56
 **/
public class ProviderMessageServiceFallback {


    public static UmsAdminLoginLog sendUmsAdminLoginLogFallBack(UmsAdminLoginLog umsAdminLoginLog, Throwable ex) {
        ex.printStackTrace();
        umsAdminLoginLog.setFallbackReason("Dubbo 服务熔断出错" + ex.toString().substring(230));
        return umsAdminLoginLog;
    }

}
