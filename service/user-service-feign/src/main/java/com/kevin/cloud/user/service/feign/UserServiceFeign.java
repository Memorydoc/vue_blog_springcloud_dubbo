package com.kevin.cloud.user.service.feign;

import com.kevin.cloud.configuration.fegin.configuration.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName UserServiceFeign
 * Description TODO
 * @Author pwc kevin
 * @Date 1/6/2020 3:43 PM
 * Version 1.0
 **/
@FeignClient(value = "user-service" ,path = "user" ,configuration = FeignRequestConfiguration.class)
public class UserServiceFeign {

}
