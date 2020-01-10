package com.kevin.cloud.cloud.message.rocketmessage;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageSource {

    @Output("admin-login-log-topic")
    MessageChannel adminLoginLog();

}
