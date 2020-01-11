package com.kevin.cloud.message.api;

import com.kevin.cloud.message.domain.UmsAdminLoginLog;
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
