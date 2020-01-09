package com.kevin.cloud.user.service.feign.fallback;

import com.kevin.cloud.commons.dto.ResponseResult;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.user.service.feign.UserServiceFeign;
import com.kevin.cloud.user.service.feign.dto.UmsAdminDTO;
import org.springframework.stereotype.Component;


/**
 * @author 司泽刚
 */
@Component
public class UserServiceFeignFallBack implements UserServiceFeign {
    public static final String BREAKING_MESSAGE = "请求失败了，请检查您的网络";

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
