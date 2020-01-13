package com.kevin.cloud.message;

import com.kevin.cloud.message.sink.MessageSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-11 14:31
 **/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableBinding({MessageSink.class})
public class MessageCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageCustomerApplication.class, args);
    }

}