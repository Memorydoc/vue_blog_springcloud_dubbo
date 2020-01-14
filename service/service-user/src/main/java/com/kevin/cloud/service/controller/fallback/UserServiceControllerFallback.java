package com.kevin.cloud.service.controller.fallback;

import com.kevin.cloud.commons.platform.dto.QueryPageParam;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
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

    /**
     * @param queryPageParam 分页查询参数
     * @param ex 异常
     * @return
     */
    public static ResponseResult<UmsAdminDTO> queryByPageUmsAdminLoginLogFallback(QueryPageParam queryPageParam, Throwable ex) {
        logger.warn("Invoke infoFallback: " + ex.getClass().getTypeName());
        ex.printStackTrace();
        return new ResponseResult(ResponseResult.CodeStatus.BREAKING, UserServiceFeignFallBack.BREAKING_MESSAGE, "发生异常");
    }

}
