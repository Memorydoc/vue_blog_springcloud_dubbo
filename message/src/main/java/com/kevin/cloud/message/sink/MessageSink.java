package com.kevin.cloud.message.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageSink {

    @Input("admin-login-log-topic")
    SubscribableChannel adminLoginLog();

}
