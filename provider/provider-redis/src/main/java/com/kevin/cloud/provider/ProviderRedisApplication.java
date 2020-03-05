package com.kevin.cloud.provider;

import com.kevin.cloud.provider.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: vue-blog-backend
 * @description: redis服务
 * @author: kevin
 * @create: 2020-01-23 21:15
 **/

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackageClasses = {ProviderRedisApplication.class, DubboSentinelConfiguration.class}, scanBasePackages = "com.kevin.cloud.provider")
@EnableDiscoveryClient
public class ProviderRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderRedisApplication.class, args);
    }
}
