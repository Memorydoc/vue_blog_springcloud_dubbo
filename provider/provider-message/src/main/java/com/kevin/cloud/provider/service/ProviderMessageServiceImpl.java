package com.kevin.cloud.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.kevin.cloud.provider.api.ProviderMessage;
import com.kevin.cloud.provider.domain.UmsAdminLoginLog;
import com.kevin.cloud.provider.mapper.UmsAdminLoginLogMapper;
import com.kevin.cloud.provider.service.fallback.ProviderMessageServiceFallback;
import com.kevin.cloud.platform.dto.FallBackResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-11 13:56
 **/
@Service(version = "1.0.0")
public class ProviderMessageServiceImpl implements ProviderMessage {

    @Autowired
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;


    @SentinelResource(value = "sendAdminLoginLog", fallback = "sendUmsAdminLoginLogFallBack", fallbackClass = ProviderMessageServiceFallback.class)
    @Override
    public FallBackResult insert(UmsAdminLoginLog umsAdminLoginLog) {
        FallBackResult fallBackResult = new FallBackResult();
        int insert = umsAdminLoginLogMapper.insertSelective(umsAdminLoginLog);
        if (insert > 0) {
            fallBackResult.setStatus(true);
            return fallBackResult;
        }
        fallBackResult.setStatus(false);
        return fallBackResult;
    }
}
