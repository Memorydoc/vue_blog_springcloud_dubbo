package com.kevin.cloud.provider;

import com.kevin.cloud.provider.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname ProviderTransactionApplication
 * @Description TODO
 * @Date 2020/2/28 23:51
 * @Created by kevin
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class,  DataSourceTransactionManagerAutoConfiguration.class },
        scanBasePackageClasses = {DubboSentinelConfiguration.class, ProviderTransactionApplication.class}) //因为PageHelper 插件中有连接数据库的配置，所以在这里exclude去掉数据库配置
@EnableDiscoveryClient
public class ProviderTransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderTransactionApplication.class, args);
    }
}
