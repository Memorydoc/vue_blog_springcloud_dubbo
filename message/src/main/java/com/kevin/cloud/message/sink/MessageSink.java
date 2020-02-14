package com.kevin.cloud.message.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageSink {

    @Input("admin-login-log-topic")
    SubscribableChannel adminLoginLog();


    @Input("truist-login")
    SubscribableChannel truistLogin(); // 游客登录 异步发送登录成功消息， 如果是手机用户登录的话


}
