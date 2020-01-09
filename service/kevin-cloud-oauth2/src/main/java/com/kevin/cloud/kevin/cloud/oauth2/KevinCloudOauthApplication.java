package com.kevin.cloud.kevin.cloud.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackageClasses = {KevinCloudOauthApplication.class}, scanBasePackages = "com.kevin.cloud.user.service.feign")
public class KevinCloudOauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(KevinCloudOauthApplication.class, args);
    }
}
