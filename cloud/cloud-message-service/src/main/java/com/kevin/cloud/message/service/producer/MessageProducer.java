package com.kevin.cloud.message.service.producer;

import com.kevin.cloud.message.service.rocketmessage.MessageSource;
import com.kevin.cloud.provider.api.CloudMessageService;
import com.kevin.cloud.platform.dto.MessageCommonDto;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * KevinCloud 消息生产者 即是cloud的feign服务也是dubbo的rpc服务
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
