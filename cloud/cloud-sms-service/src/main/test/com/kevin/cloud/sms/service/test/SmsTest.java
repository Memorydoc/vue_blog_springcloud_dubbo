package com.kevin.cloud.sms.service.test;

import com.aliyuncs.exceptions.ClientException;
import com.kevin.cloud.commons.dto.cloud.vo.SmsVo;
import com.kevin.cloud.sms.service.impl.SmsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.sms.service.test
 * @ClassName: SmsTest
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/10 12:42
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SmsTest {

    @Autowired
    private SmsServiceImpl smsService;

    @Test
    public void sendSms(){
        SmsVo smsVo = new SmsVo();
        smsVo.setPhoneNumber("17615195790");
        smsService.sendSmsDefault(smsVo);
    }

}
