package com.kevin.cloud.message.service.controller;

import com.kevin.cloud.message.service.producer.MessageProducer;
import com.kevin.cloud.commons.platform.dto.MessageCommonDto;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 消息控制器
 * @author: kevin
 * @create: 2020-01-10 17:06
 **/
@RestController
@RequestMapping(value = "message")
public class MessageController {

    @Resource
    private MessageProducer messageProducer;


    @RequestMapping("sendAdminLog")
    public ResponseResult sendAdminLoginLog(@RequestBody MessageCommonDto messageCommonDto){
        boolean b = messageProducer.sendAdminLoginLog(messageCommonDto);
        if(b){
            return  new ResponseResult(ResponseResult.CodeStatus.OK, "日志发送成功");
        }else{
            return  new ResponseResult(ResponseResult.CodeStatus.FAIL, "日志发送失败");
        }
    }
}
