package com.kevin.cloud.provider;

import com.kevin.cloud.provider.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-18 12:25
 **/
@SpringBootApplication(scanBasePackageClasses = {ProviderElasticsearchApplication.class, DubboSentinelConfiguration.class},exclude= {DataSourceAutoConfiguration.class})
public class ProviderElasticsearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderElasticsearchApplication.class, args);
    }
}
