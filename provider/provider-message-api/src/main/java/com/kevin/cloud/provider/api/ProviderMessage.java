package com.kevin.cloud.provider.api;

import com.kevin.cloud.provider.domain.UmsAdminLoginLog;
import com.kevin.cloud.platform.dto.FallBackResult;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 消息dubbo接口
 * @author: kevin
 * @create: 2020-01-11 14:00
 **/


public interface ProviderMessage {
    public FallBackResult insert(UmsAdminLoginLog umsAdminLoginLog);
}
