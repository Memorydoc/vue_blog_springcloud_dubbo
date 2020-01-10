package com.kevin.cloud.user.provider;


import com.kevin.cloud.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackageClasses = {UserProviderServiceApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.kevin.cloud.user.provider.mapper")
public class UserProviderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserProviderServiceApplication.class, args);
    }
}
