package com.kevin.cloud.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class}, scanBasePackageClasses = {ServiceUserApplication.class, CustomOauthException.class}) //因为PageHelper 插件中有连接数据库的配置，所以在这里exclude去掉数据库配置
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
public class ServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }
}
