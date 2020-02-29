package com.kevin.cloud.provider;

import com.kevin.cloud.provider.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-15 14:19
 **/
@SpringBootApplication(scanBasePackageClasses = {ProviderArticleApplication.class, DubboSentinelConfiguration.class},
scanBasePackages = {"com.kevin.cloud.provider.service"})
@MapperScan(basePackages = "com.kevin.cloud.provider.mapper")
@EnableTransactionManagement
@EnableDiscoveryClient
public class ProviderArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderArticleApplication.class, args);
    }
}