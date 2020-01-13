package com.kevin.cloud.service.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.kevin.cloud.commons.dto.UmsAdminLoginLogDto;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.QueryPageParam;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.provider.api.ProviderLogService;
import com.kevin.cloud.service.feign.dto.UmsAdminDTO;
import com.kevin.cloud.service.controller.fallback.UserServiceControllerFallback;
import com.kevin.cloud.user.api.UserService;
import com.kevin.cloud.user.domain.UmsAdmin;
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
    public String sayHello(String username) {
        return userService.sayHello(username);
    }


    /**
     * @return {@link ResponseResult}
     * @Param {@link UmsAdmin}
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
        System.out.println(1 / 0);
        UmsAdmin umsAdmin = userService.get(username);
        UmsAdminDTO umsAdminDTO = new UmsAdminDTO();
        BeanUtils.copyProperties(umsAdmin, umsAdminDTO);
        return new ResponseResult<UmsAdminDTO>(ResponseResult.CodeStatus.OK, "获取个人信息", umsAdminDTO);
    }


    @Reference(version = "1.0.0")
    private ProviderLogService providerLogService;


    //这是测试 分页的方法
    @PostMapping(value = "queryByPage")
    public ResponseResult<UmsAdminLoginLogDto> queryByPageUmsAdminLoginLog(@RequestBody QueryPageParam queryPageParam) {
        FallBackResult fallBackResult = providerLogService.queryUserLoginLogByPage(queryPageParam);
        if (fallBackResult.isStatus()) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分页获取登录日志成功", fallBackResult.getData());
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分页获取登录日志失败", null);
        }
    }


}
