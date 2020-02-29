package com.kevin.cloud.provider.config;

import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname SeataConfiguration
 * @Description TODO
 * @Date 2020/2/25 18:11
 * @Created by kevin
 */
@Configuration
public class SeataConfiguration {
    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner("provider-transaction", "tx_group");
    }
}