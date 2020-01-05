package com.kevin.cloud.kevin.cloud.oauth2.controller;

import com.google.common.collect.Maps;
import com.kevin.cloud.commons.dto.ResponseResult;
import com.kevin.cloud.commons.dto.ServiceException;
import com.kevin.cloud.commons.dto.ServiceStatus;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.commons.utils.OkHttpClientUtil;
import com.kevin.cloud.kevin.cloud.oauth2.dto.LoginParam;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class LoginController {
    @Value("${business.oauth2.grant_type}")
    public String oauth2GrantType;

    @Value("${business.oauth2.client_id}")
    public String oauth2ClientId;

    @Value("${business.oauth2.client_secret}")
    public String oauth2ClientSecret;
    @Resource
    public UserDetailsService userDetailsService;

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    @PostMapping(value = "/user/login")
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
}
