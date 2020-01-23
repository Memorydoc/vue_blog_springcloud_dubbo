package com.kevin.cloud.service.feign;

import com.kevin.cloud.commons.dto.user.dto.UmsAdminDto;
import com.kevin.cloud.configuration.fegin.configuration.FeignRequestConfiguration;
import com.kevin.cloud.service.feign.fallback.UserServiceFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName UserServiceFeign
 * Description TODO
 * @Author pwc kevin
 * @Date 1/6/2020 3:43 PM
 * Version 1.0
 **/
@FeignClient(value = "service-user" ,path = "user" ,configuration = FeignRequestConfiguration.class
, fallback = UserServiceFeignFallBack.class)
public interface UserServiceFeign {
    /**
     * 获取个人信息
     *
     * @param username {@code String} 用户名
     * @return {@code String} JSON
     */
    @GetMapping(value = "info/{username}")
    String info(@PathVariable String username);

}
