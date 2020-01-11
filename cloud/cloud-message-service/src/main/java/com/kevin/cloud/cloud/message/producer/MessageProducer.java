package com.kevin.cloud.cloud.message.producer;

import com.kevin.cloud.cloud.message.dto.UmsAdminLoginLogDTO;
import com.kevin.cloud.cloud.message.rocketmessage.MessageSource;
import com.kevin.cloud.commons.dto.MessageCommonDto;
import com.kevin.cloud.message.api.CloudMessageService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息生产者
 */
@Component
@Service(version = "1.0.0")
public class MessageProducer implements CloudMessageService {

    @Resource
    private MessageSource source;

    /**
     * 管理登录日志
     * @return {@code boolean}
     */

    @Override
    public boolean sendAdminLoginLog(MessageCommonDto dto) {
        return source.adminLoginLog().send(MessageBuilder.withPayload(dto).build());
    }
}
