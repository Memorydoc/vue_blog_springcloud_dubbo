package com.kevin.cloud.user;


import com.kevin.cloud.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackageClasses = {ProviderUserServiceApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.kevin.cloud.user.mapper")
public class ProviderUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderUserServiceApplication.class, args);
    }
}
