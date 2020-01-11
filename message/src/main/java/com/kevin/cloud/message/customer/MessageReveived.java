package com.kevin.cloud.message.customer;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.message.api.ProviderMessage;
import com.kevin.cloud.message.domain.UmsAdminLoginLog;
import com.kevin.cloud.platform.dto.FallBackResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class MessageReveived {

    @Reference(version = "1.0.0")
    private ProviderMessage  providerMessage;

    @StreamListener("admin-login-log-topic")
    public void receiveAdminLoginLog(String umsAdminLoginLogJson) throws Exception {
        System.out.println(umsAdminLoginLogJson);
        UmsAdminLoginLog umsAdminLoginLog = MapperUtils.json2pojoByFastJson(umsAdminLoginLogJson, UmsAdminLoginLog.class);
        System.out.println(umsAdminLoginLog);
        FallBackResult fallBackResult = providerMessage.insert(umsAdminLoginLog);
        System.out.println(fallBackResult);
    }

}
