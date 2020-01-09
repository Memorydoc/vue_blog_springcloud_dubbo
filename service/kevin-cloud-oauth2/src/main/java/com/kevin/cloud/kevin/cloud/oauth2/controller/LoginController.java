package com.kevin.cloud.kevin.cloud.oauth2.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.collect.Maps;
import com.kevin.cloud.commons.dto.ResponseResult;
import com.kevin.cloud.commons.dto.ServiceException;
import com.kevin.cloud.commons.dto.ServiceStatus;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.commons.utils.OkHttpClientUtil;
import com.kevin.cloud.kevin.cloud.oauth2.dto.LoginParam;
import com.kevin.cloud.kevin.cloud.oauth2.service.UserDetailsServiceImpl;
import com.kevin.cloud.user.provider.api.UserService;
import com.kevin.cloud.user.provider.api.domain.UmsAdmin;
import com.kevin.cloud.user.service.feign.UserServiceFeign;
import okhttp3.Response;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("oauth")
public class LoginController {
    @Value("${business.oauth2.grant_type}")
    public String oauth2GrantType;

    @Value("${business.oauth2.client_id}")
    public String oauth2ClientId;

    @Value("${business.oauth2.client_secret}")
    public String oauth2ClientSecret;
    @Reference(version = "1.0.0")
    private UserService userService;
    @Resource(name = "userDetailsServiceBean")
    public UserDetailsService userDetailsService;
    @Resource
    public BCryptPasswordEncoder passwordEncoder;
    
    @Resource
    private UserServiceFeign userServiceFeign;

    @PostMapping(value = "login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam, HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        // 验证密码是否正确
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.FAIL, "账号密码错误", null);
        }
        String url = "http://localhost:9001/oauth/token";
        map.put("username", loginParam.getUsername());
        map.put("password", loginParam.getPassword());
        map.put("grant_type", oauth2GrantType);
        map.put("client_id", oauth2ClientId);
        map.put("client_secret", oauth2ClientSecret);
        Response response = OkHttpClientUtil.getInstance().postData(url, map);
        String jsonString = Objects.requireNonNull(response.body()).string();
        Map<String, Object> stringObjectMap = MapperUtils.json2map(jsonString);
        String accessToken = stringObjectMap.get("access_token").toString();
        Map result = Maps.newHashMap();
        result.put("token", accessToken);
        return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.OK, "登录成功", result);
    }

    @GetMapping(value = "info/{username}")
    @SentinelResource(value = "info", fallback = "infoFallback")
    public ResponseResult<UmsAdmin> info(@PathVariable String username) {
        UmsAdmin umsAdmin = userService.get(username);
        return new ResponseResult<UmsAdmin>(ResponseResult.CodeStatus.OK, "获取个人信息", umsAdmin);
    }

    @GetMapping(value = "infoByFeign")
    @SentinelResource(value = "user/infoByFeign", fallback = "infoFallback")
    public ResponseResult<UmsAdmin> infoByFeign() throws Exception {
        // 获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取个人信息
        String jsonString = userServiceFeign.info(authentication.getName());

        UmsAdmin umsAdmin = MapperUtils.json2pojoByTree(jsonString, "data", UmsAdmin.class);

        // 如果触发熔断则返回熔断结果
        if (umsAdmin == null) {
            return MapperUtils.json2pojo(jsonString, ResponseResult.class);
        }
        return new ResponseResult<UmsAdmin>(ResponseResult.CodeStatus.OK, "通过feign获取个人信息", umsAdmin);
    }
    
    

}
