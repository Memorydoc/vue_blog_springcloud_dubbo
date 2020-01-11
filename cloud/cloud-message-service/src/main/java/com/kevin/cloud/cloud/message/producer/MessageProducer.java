package com.kevin.cloud.cloud.message.producer;

import com.kevin.cloud.cloud.message.dto.UmsAdminLoginLogDTO;
import com.kevin.cloud.cloud.message.rocketmessage.MessageSource;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息生产者
 */
@Component
public class MessageProducer {

    @Resource
    private MessageSource source;

    /**
     * 管理登录日志
     * @return {@code boolean}
     */
    public boolean sendAdminLoginLog(UmsAdminLoginLogDTO dto) {
        return source.adminLoginLog().send(MessageBuilder.withPayload(dto).build());
    }
}
