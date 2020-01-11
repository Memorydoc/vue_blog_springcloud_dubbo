package com.kevin.cloud.message.api;

import com.kevin.cloud.commons.dto.MessageCommonDto;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 云消息平台接口库
 * @author: kevin
 * @create: 2020-01-11 14:08
 **/

public interface CloudMessageService {

    public boolean sendAdminLoginLog(MessageCommonDto dto);
}
