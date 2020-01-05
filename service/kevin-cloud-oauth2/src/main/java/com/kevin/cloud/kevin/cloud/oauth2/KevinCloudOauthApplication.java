package com.kevin.cloud.kevin.cloud.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KevinCloudOauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(KevinCloudOauthApplication.class, args);
    }
}
