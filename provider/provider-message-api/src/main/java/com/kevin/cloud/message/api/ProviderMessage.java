package com.kevin.cloud.message.api;

import com.kevin.cloud.message.domain.UmsAdminLoginLog;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 消息dubbo接口
 * @author: kevin
 * @create: 2020-01-11 14:00
 **/


public interface ProviderMessage {
    public boolean insert(UmsAdminLoginLog umsAdminLoginLog);
}
