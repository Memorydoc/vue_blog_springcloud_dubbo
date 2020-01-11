package com.kevin.cloud.service.controller.fallback;

import com.kevin.cloud.platform.dto.ResponseResult;
import com.kevin.cloud.service.feign.dto.UmsAdminDTO;
import com.kevin.cloud.service.feign.fallback.UserServiceFeignFallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户管理服务熔断器
 */
public class UserServiceControllerFallback {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceControllerFallback.class);

    /**
     * 熔断方法
     *
     * @param username {@code String} 用户名
     */
    public static ResponseResult<UmsAdminDTO> infoFallback(String username, Throwable ex) {
        UmsAdminDTO umsAdminDTO = new UmsAdminDTO();
        umsAdminDTO.setUsername("我是cloud熔断后的用户");
        logger.warn("Invoke infoFallback: " + ex.getClass().getTypeName());
        ex.printStackTrace();
        return new ResponseResult<UmsAdminDTO>(ResponseResult.CodeStatus.BREAKING, UserServiceFeignFallBack.BREAKING_MESSAGE, umsAdminDTO);
    }

}
