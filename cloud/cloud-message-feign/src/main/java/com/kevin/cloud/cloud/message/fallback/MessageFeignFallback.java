package com.kevin.cloud.cloud.message.fallback;

import com.kevin.cloud.cloud.message.MessageFeign;
import com.kevin.cloud.cloud.message.dto.UmsAdminLoginLogDTO;
import com.kevin.cloud.platform.dto.CloudBaseFeign;
import org.springframework.stereotype.Component;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-10 17:08
 **/
@Component
public class MessageFeignFallback extends CloudBaseFeign implements MessageFeign {

    @Override
    public String sendAdminLoginLog(UmsAdminLoginLogDTO umsAdminLoginLogDTO) {
        return error();
    }
}
