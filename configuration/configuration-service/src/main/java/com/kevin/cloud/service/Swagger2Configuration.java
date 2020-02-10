package com.kevin.cloud.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service
 * @ClassName: Swagger2Configuration
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/7 18:40
 * @Version: 1.0
 */
@Configuration
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kevin.cloud.service.impl"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Memorydoc Blog  文档")
                .description("Memorydoc Blog  网关接口，https://www.sizegang.cn")
                .termsOfServiceUrl("https://www.sizegang.cn")
                .version("1.0.0")
                .build();
    }
}