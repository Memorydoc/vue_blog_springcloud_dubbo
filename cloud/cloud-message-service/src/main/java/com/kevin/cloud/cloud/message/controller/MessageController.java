package com.kevin.cloud.cloud.message.controller;

import com.kevin.cloud.cloud.message.producer.MessageProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 日志服务接口
 * @author: kevin
 * @create: 2020-01-10 17:06
 **/
@RestController
@RequestMapping(value = "message")
public class MessageController {

    @Resource
    private MessageProducer messageProducer;

    /*@RequestMapping
    public ResponseResult sendAdminLoginLog(){

    }*/
}
