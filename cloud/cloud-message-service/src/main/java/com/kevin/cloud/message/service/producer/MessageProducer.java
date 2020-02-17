package com.kevin.cloud.message.service.producer;

import com.kevin.cloud.message.service.rocketmessage.MessageSource;
import com.kevin.cloud.message.api.CloudMessageService;
import com.kevin.cloud.commons.platform.dto.MessageCommonDto;
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

    /**
     * @param phone 登录手机号
     * @return
     */
    @Override
    public boolean truistLogin(String phone) {
        System.out.println("消息生产者发送消息开始：发送游客登录成功的消息");
        return  source.truistLogin().send(MessageBuilder.withPayload(phone).build());
    }
}
