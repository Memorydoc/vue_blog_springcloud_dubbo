package com.kevin.cloud.provider;

import com.kevin.cloud.provider.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-11 13:06
 **/
@SpringBootApplication(scanBasePackageClasses = {ProviderMessageApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.kevin.cloud.message.mapper")
public class ProviderMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderMessageApplication.class,args);
    }
}
