package com.kevin.cloud.message.service.test;

import com.kevin.cloud.message.service.rocketmessage.MessageSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.cloud.test
 * @ClassName: CloudMessageTest
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/19 0:17
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CloudMessageTest {
    @Resource
    private MessageSource source;

    @Test
    public void testSendMessage() {
        System.out.println("开始测试");
        source.truistLogin().send(MessageBuilder.withPayload("17615195790").build());
    }

}
