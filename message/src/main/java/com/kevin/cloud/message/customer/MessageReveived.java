package com.kevin.cloud.message.customer;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.message.api.ProviderMessage;
import com.kevin.cloud.message.domain.UmsAdminLoginLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class MessageReveived {

    @Reference(version = "1.0.0")
    private ProviderMessage  providerMessage;

    @StreamListener("admin-login-log-topic")
    public void receiveAdminLoginLog(String umsAdminLoginLogJson) throws Exception {
        UmsAdminLoginLog umsAdminLoginLog = MapperUtils.json2pojo(umsAdminLoginLogJson, UmsAdminLoginLog.class);
        System.out.println(umsAdminLoginLog);
        providerMessage.insert(umsAdminLoginLog);
    }

}
