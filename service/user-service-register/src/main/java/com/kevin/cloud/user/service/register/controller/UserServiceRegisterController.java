package com.kevin.cloud.user.service.register.controller;


import com.kevin.cloud.commons.dto.ResponseResult;
import com.kevin.cloud.user.provider.api.UserService;
import com.kevin.cloud.user.provider.api.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册服务。
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/register")
@RestController
public class UserServiceRegisterController {

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
    @PostMapping(value = "")
    public ResponseResult<UmsAdmin> reg(@RequestBody UmsAdmin umsAdmin) {
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



}
