package com.kevin.cloud.sms.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.sms.service
 * @ClassName: CloudSmsServiceApplication
 *
 * @Author: kevin
 * @Description: 短信云接口
 * @Date: 2020/2/10 11:20
 * @Version: 1.0
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class CloudSmsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudSmsServiceApplication.class, args);
    }

}
