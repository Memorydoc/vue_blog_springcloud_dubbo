package com.kevin.cloud.provider;

import com.kevin.cloud.provider.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: vue-blog-backend
 * @description: 博客 公共模块服务  博客功能模块服务，菜单，栏目，友情连接等等
 * @author: kevin
 * @create: 2020-01-14 20:44
 **/
@SpringBootApplication(scanBasePackageClasses = {ProviderBlogApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.kevin.cloud.provider.mapper")
public class ProviderBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderBlogApplication.class, args);
    }
}
