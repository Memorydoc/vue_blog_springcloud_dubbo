package com.kevin.cloud.cloud.message;

import com.kevin.cloud.cloud.message.rocketmessage.MessageSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 日志服务
 * @author: kevin
 * @create: 2020-01-10 16:50
 **/

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({MessageSource.class}) // 配置消息绑定器
public class CloudMessageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudMessageServiceApplication.class, args);
    }
}
