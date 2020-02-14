package com.kevin.cloud.message.customer;
import com.kevin.cloud.commons.dto.cloud.dto.SmsDto;
import com.kevin.cloud.commons.dto.cloud.vo.SmsVo;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.provider.api.ProviderLogService;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.provider.domain.UmsAdminLoginLog;
import com.kevin.cloud.sms.api.SmsService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class MessageReveived {
    Logger logger = LoggerFactory.getLogger(MessageReveived.class);

    @Reference(version = "1.0.0")
    private ProviderLogService providerLogService;

    @StreamListener("admin-login-log-topic")
    public void receiveAdminLoginLog(String umsAdminLoginLogJson) throws Exception {
        System.out.println(umsAdminLoginLogJson);
        UmsAdminLoginLog umsAdminLoginLog = MapperUtils.json2pojoByFastJson(umsAdminLoginLogJson, UmsAdminLoginLog.class);
        System.out.println(umsAdminLoginLog);
        FallBackResult fallBackResult = providerLogService.insert(umsAdminLoginLog);
        System.out.println(fallBackResult);
    }

    @Reference(version = "1.0.0")
    private SmsService smsService;

    private static  final  String truistTemplateCode = "SMS_183248153";
    private static  final  String memorydocSignGeneral = "Memorydoc";

    // 游客登录 发送登录成功短信
    @StreamListener("truist-login")
    public void truistLogin(String phone) throws Exception {
        logger.info(String.format("手机号为%s, 登录成功", phone));
        SmsVo smsVo = new SmsVo();
        smsVo.setPhoneNumber(phone);
        SmsDto smsDto = smsService.sendSms(smsVo, truistTemplateCode, memorydocSignGeneral);
        logger.info(String.valueOf(smsDto));
    }

}
