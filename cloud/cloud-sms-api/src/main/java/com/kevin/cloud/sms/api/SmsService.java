package com.kevin.cloud.sms.api;

import com.kevin.cloud.commons.dto.cloud.dto.SmsDto;
import com.kevin.cloud.commons.dto.cloud.dto.SmsQueryDto;
import com.kevin.cloud.commons.dto.cloud.dto.SmsSendDetailDTO;
import com.kevin.cloud.commons.dto.cloud.vo.SmsVo;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.sms.api
 * @ClassName: SmsService
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/10 13:42
 * @Version: 1.0
 */
public interface SmsService {
    public SmsDto sendSms(SmsVo smsVo, String templateCode, String signName);

    // 发送短信 使用默认模板
    public SmsDto sendSmsDefault(SmsVo smsVo);
    // 查询发送的短信
    public SmsSendDetailDTO querySendDetails(SmsVo smsVo) throws Exception;
}
