package com.kevin.cloud.message.service.rocketmessage;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 消息绑定接口（消息绑定器）
 */
public interface MessageSource {

    @Output("admin-login-log-topic")
    MessageChannel adminLoginLog();

    // 游客登录成功短信提醒
    @Output("truist-login")
    MessageChannel truistLogin();
}
