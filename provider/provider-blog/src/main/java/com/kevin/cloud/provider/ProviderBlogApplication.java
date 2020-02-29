package com.kevin.cloud.provider;

import com.kevin.cloud.provider.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: vue-blog-backend
 * @description: 博客 公共模块服务  博客功能模块服务，菜单，栏目，友情连接等等
 * @author: kevin
 * @create: 2020-01-14 20:44
 **/
@SpringBootApplication(scanBasePackageClasses = {ProviderBlogApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.kevin.cloud.provider.mapper")
@EnableTransactionManagement
@EnableDiscoveryClient
public class ProviderBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderBlogApplication.class, args);
    }
}
