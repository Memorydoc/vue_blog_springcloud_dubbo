package com.kevin.cloud.cloud.message.controller;

import com.kevin.cloud.cloud.message.dto.UmsAdminLoginLogDTO;
import com.kevin.cloud.cloud.message.producer.MessageProducer;
import com.kevin.cloud.commons.dto.MessageCommonDto;
import com.kevin.cloud.commons.dto.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

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


    @RequestMapping("sendAdminLog")
    public ResponseResult sendAdminLoginLog(){
        UmsAdminLoginLogDTO umsAdminLoginLogDTO = new UmsAdminLoginLogDTO();
        umsAdminLoginLogDTO.setAddress("山东日志");
        umsAdminLoginLogDTO.setCreateTime(new Date());
        umsAdminLoginLogDTO.setIp("192.168.0.132");
        MessageCommonDto messageCommonDto = new MessageCommonDto();
        BeanUtils.copyProperties(umsAdminLoginLogDTO, messageCommonDto);
        boolean b = messageProducer.sendAdminLoginLog(messageCommonDto);
        if(b){
            return  new ResponseResult(ResponseResult.CodeStatus.OK, "日志发送成功");
        }else{
            return  new ResponseResult(ResponseResult.CodeStatus.FAIL, "日志发送失败");
        }
    }
}
