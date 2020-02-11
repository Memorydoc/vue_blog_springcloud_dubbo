package com.kevin.cloud.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service
 * @ClassName: ServiceJobApplication
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/12 0:39
 * @Version: 1.0
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class},
        scanBasePackageClasses = {ServiceJobApplication.class})
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceJobApplication {
    public static void main(String[] args) {
        System.out.println("123");
        SpringApplication.run(ServiceJobApplication.class, args);
    }

}
