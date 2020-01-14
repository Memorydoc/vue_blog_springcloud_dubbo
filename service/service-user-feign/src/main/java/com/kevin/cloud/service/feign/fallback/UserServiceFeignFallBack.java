package com.kevin.cloud.service.feign.fallback;

import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.service.feign.dto.UmsAdminDTO;
import com.kevin.cloud.service.feign.UserServiceFeign;
import org.springframework.stereotype.Component;


/**
 * @author 司泽刚
 */
@Component
public class UserServiceFeignFallBack implements UserServiceFeign {
    public static final String BREAKING_MESSAGE = "用户服务发生了熔断，请检查服务健康状态";

    @Override
    public String info(String username) {
        UmsAdminDTO umsAdminDTO = new UmsAdminDTO();
        umsAdminDTO.setEmail("error.qq.com");
        try {
            return MapperUtils.obj2json(new ResponseResult<UmsAdminDTO>(ResponseResult.CodeStatus.BREAKING, BREAKING_MESSAGE, umsAdminDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
