package com.kevin.cloud.service.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.dto.log.dto.UmsAdminLoginLogDto;
import com.kevin.cloud.commons.dto.user.dto.UmsAdminDto;
import com.kevin.cloud.commons.dto.user.vo.UmsAdminVo;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.provider.api.ProviderLogService;
import com.kevin.cloud.service.controller.fallback.UserServiceControllerFallback;
import com.kevin.cloud.user.api.UserService;
import com.kevin.cloud.user.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseResult<UmsAdminDto> info(@PathVariable String username) {
        UmsAdmin umsAdmin = userService.get(username);
        UmsAdminDto umsAdminDTO = new UmsAdminDto();
        BeanUtils.copyProperties(umsAdmin, umsAdminDTO);
        return new ResponseResult<UmsAdminDto>(ResponseResult.CodeStatus.OK, "获取个人信息", umsAdminDTO);
    }


    @Reference(version = "1.0.0")
    private ProviderLogService providerLogService;


    //这是测试 分页的方法
    @PostMapping(value = "queryByPage")
    @SentinelResource(value = "queryByPage", fallback = "queryByPageUmsAdminLoginLogFallback", fallbackClass = UserServiceControllerFallback.class)
    public ResponseResult<UmsAdminLoginLogDto> queryByPageUmsAdminLoginLog(@RequestBody QueryPageParam queryPageParam) {
        FallBackResult fallBackResult = providerLogService.queryUserLoginLogByPage(queryPageParam);
        if (fallBackResult.isStatus()) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分页获取登录日志成功", fallBackResult.getData());
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分页获取登录日志失败", null);
        }
    }

    //获取 用户列表
    @PostMapping("userList")
    public ResponseResult userList(@RequestBody UmsAdminVo umsAdminVo) {
        PageResult pageResult = userService.userList(umsAdminVo);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "用户列表分页查询成功", pageResult);
    }

    //删除用户
    @PostMapping("deleteUser")
    public ResponseResult deleteUser(@RequestBody UmsAdminVo umsAdminVo) {
        int count = userService.deleteUser(umsAdminVo);
        if (count > 0) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "用户删除成功", null);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "用户不存在", null);
        }
    }

    // 修改用户信息
    @PostMapping("editUser")
    public ResponseResult editUser(@RequestBody UmsAdminVo umsAdminVo) {
        int count = userService.editUser(umsAdminVo);
        if (count >= 1) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "修改成功", null);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "修改失败", null);
        }
    }


    /**
     * 获取当前登录人信息
     */
    @GetMapping(value = "getCurrentUser")
    public UmsAdminDto getCurrentUser() {
        System.out.println("正在获取");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UmsAdmin umsAdmin = userService.get(authentication.getName());
        UmsAdminDto umsAdminDTO = new UmsAdminDto();
        BeanUtils.copyProperties(umsAdmin, umsAdminDTO);
        System.out.println("当前登录人为" + umsAdmin.getUsername());
        return umsAdminDTO;
    }

}
