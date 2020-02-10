package com.kevin.cloud.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.kevin.cloud.commons.dto.cloud.dto.SmsDto;
import com.kevin.cloud.commons.dto.cloud.dto.SmsQueryDto;
import com.kevin.cloud.commons.dto.cloud.dto.SmsSendDetailDTO;
import com.kevin.cloud.commons.dto.cloud.vo.SmsVo;
import com.kevin.cloud.commons.utils.CommonUtils;
import com.kevin.cloud.commons.utils.DateUtils;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.sms.api.SmsService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.sms.service.impl
 * @ClassName: SmsController
 * @Author: kevin
 * @Description: 短信服务接口
 * @Date: 2020/2/10 11:22
 * @Version: 1.0
 */

@Service(version = "1.0.0")
public class SmsServiceImpl implements SmsService {


    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    @Value("${base.config.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${base.config.sms.accessSecret}")
    private String accessSecret;

    @Value("${base.config.sms.blog-register.templateCode}")
    private String registerTemplateCode;

    @Value("${base.config.sms.blog-register.signName}")
    private String registerSignName;


    /**
     * 发送短信
     *
     * @param smsVo
     * @param templateCode 模板code
     * @param signName     签名名称
     * @return
     * @throws ClientException
     */
    @Override
    public SmsDto sendSms(SmsVo smsVo, String templateCode, String signName) {
        String code = CommonUtils.createSmsCode();
        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25"); // 写死的都是固定的不能变
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", smsVo.getPhoneNumber());
        request.putQueryParameter("SignName", signName == null ? registerSignName : signName);
        request.putQueryParameter("TemplateCode", templateCode == null ? registerTemplateCode : templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\" : \"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            SmsDto smsDto = MapperUtils.json2pojoByFastJson(response.getData(), SmsDto.class);
            smsDto.setRandomCode(code);
            return smsDto;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SmsDto sendSmsDefault(SmsVo smsVo) {
        return sendSms(smsVo, null, null);
    }


    @Override
    public  SmsSendDetailDTO querySendDetails(SmsVo smsVo){

        //可自助调整超时时间
      //  System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
       // System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId , accessSecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(smsVo.getPhoneNumber());
        //可选-流水号
        request.setBizId(smsVo.getBizId());
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = null;
        try {
            querySendDetailsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        SmsSendDetailDTO smsSendDetailDTO = new SmsSendDetailDTO();
        if(querySendDetailsResponse.getSmsSendDetailDTOs().size() == 0){
            return  null;
        }
        BeanUtils.copyProperties(querySendDetailsResponse.getSmsSendDetailDTOs().get(0), smsSendDetailDTO);
        return smsSendDetailDTO ;
    }

}
