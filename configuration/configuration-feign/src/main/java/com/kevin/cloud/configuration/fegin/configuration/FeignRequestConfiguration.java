package com.kevin.cloud.configuration.fegin.configuration;

import com.kevin.cloud.configuration.fegin.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign 全局配置
 * <p>
 */
@Configuration
public class FeignRequestConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }

}
