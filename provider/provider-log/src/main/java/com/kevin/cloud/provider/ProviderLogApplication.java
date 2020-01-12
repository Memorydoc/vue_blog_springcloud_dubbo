package com.kevin.cloud.provider;

import com.kevin.cloud.provider.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 统一日志服务
 * @author: kevin
 * @create: 2020-01-12 22:39
 **/
@SpringBootApplication(scanBasePackageClasses = {ProviderLogApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.kevin.cloud.provider.mapper")
public class ProviderLogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderLogApplication.class,args);
    }
}