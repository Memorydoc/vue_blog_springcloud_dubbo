package com.kevin.cloud.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackageClasses = {ServiceOauth2Application.class}, scanBasePackages = "com.kevin.cloud.user.service.feign")
public class ServiceOauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOauth2Application.class, args);
    }
}
