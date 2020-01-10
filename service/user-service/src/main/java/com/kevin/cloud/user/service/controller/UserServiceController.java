package com.kevin.cloud.user.service.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.kevin.cloud.commons.dto.ResponseResult;
import com.kevin.cloud.user.provider.api.UserService;
import com.kevin.cloud.user.provider.api.domain.UmsAdmin;
import com.kevin.cloud.user.service.controller.fallback.UserServiceControllerFallback;
import com.kevin.cloud.user.service.feign.UserServiceFeign;
import com.kevin.cloud.user.service.feign.dto.UmsAdminDTO;
import com.kevin.cloud.user.service.feign.fallback.UserServiceFeignFallBack;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 用户注册服务。
 */
@RequestMapping("user")
@RestController
public class UserServiceController {

    @Reference(version = "1.0.0")
    private UserService userService;

    @GetMapping("sayHello")
    public String sayHello(String username){
        return  userService.sayHello(username);
    }


    /**
     * @Param {@link UmsAdmin}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "register")
    public ResponseResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdmin) {
        String message = validateReg(umsAdmin);
        // 通过验证
        if (message == null) {
            int result = userService.insert(umsAdmin);

            // 注册成功
            if (result > 0) {
                UmsAdmin admin = userService.get(umsAdmin.getUsername());
                return new ResponseResult<UmsAdmin>(HttpStatus.OK.value(), "用户注册成功", admin);
            }
        }

        return new ResponseResult<UmsAdmin>(HttpStatus.NOT_ACCEPTABLE.value(), message != null ? message : "用户注册失败");
    }

    /**
     * 注册信息验证
     *
     * @param umsAdmin {@link UmsAdmin}
     * @return 验证结果
     */
    private String validateReg(UmsAdmin umsAdmin) {
        UmsAdmin admin = userService.get(umsAdmin.getUsername());

        if (admin != null) {
            return "用户名已存在，请重新输入";
        }

        return null;
    }



    @GetMapping(value = "info/{username}")
    @SentinelResource(value = "info", fallback = "infoFallback", fallbackClass = UserServiceControllerFallback.class)
    public ResponseResult<UmsAdminDTO> info(@PathVariable String username) {
        System.out.println(1/0);
        UmsAdmin umsAdmin = userService.get(username);
        UmsAdminDTO umsAdminDTO = new UmsAdminDTO();
        BeanUtils.copyProperties(umsAdmin, umsAdminDTO);
        return new ResponseResult<UmsAdminDTO>(ResponseResult.CodeStatus.OK, "获取个人信息", umsAdminDTO);
    }


}
