package com.kevin.cloud.upload.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: OSS云上传 启动类
 * @author: kevin
 * @create: 2020-01-09 13:24
 **/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class CloudUploadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudUploadServiceApplication.class, args);
    }

}
