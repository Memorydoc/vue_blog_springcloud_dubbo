package com.kevin.cloud.cloud.message;

import com.kevin.cloud.cloud.message.dto.UmsAdminLoginLogDTO;
import com.kevin.cloud.cloud.message.fallback.MessageFeignFallback;
import com.kevin.cloud.configuration.fegin.configuration.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 日志服务feign接口
 *              feign 是提供的springcloud 服务， 如果有服务需要使用，则提供feign接口供其他cloud服务使用即可
 * @author: kevin
 * @create: 2020-01-10 17:07
 **/
@FeignClient(value = "cloud-message", path = "message", configuration = FeignRequestConfiguration.class, fallback = MessageFeignFallback.class)
public interface MessageFeign {

    @PostMapping("sendAdminLoginLog")
    public String sendAdminLoginLog(UmsAdminLoginLogDTO  umsAdminLoginLogDTO);


}

