package com.kevin.cloud.user.provider.service.fallback;

import com.kevin.cloud.user.provider.api.domain.UmsAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: dubbo用户服务熔断器
 * @author: kevin
 * @create: 2020-01-09 21:25
 **/
public class UserServiceImplFallback {
    private static  final Logger logger = LoggerFactory.getLogger(UserServiceImplFallback.class);
    public static UmsAdmin getByUsernameFallback(String username, Throwable ex) {
        logger.warn("Invoke getByUsernameFallback: " + ex.getClass().getTypeName());
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername("我是被熔断的用户");
        ex.printStackTrace();
        return umsAdmin;
    }
}
