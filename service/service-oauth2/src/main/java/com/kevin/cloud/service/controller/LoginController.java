package com.kevin.cloud.service.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.nacos.client.utils.StringUtils;
import com.google.common.collect.Maps;
import com.kevin.cloud.commons.dto.IpInfo;
import com.kevin.cloud.commons.dto.user.dto.UmsAdminDto;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.commons.utils.OkHttpClientUtil;
import com.kevin.cloud.commons.utils.UserAgentUtils;
import com.kevin.cloud.commons.platform.dto.MessageCommonDto;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.provider.api.RedisTemplateService;
import com.kevin.cloud.service.BusinessException;
import com.kevin.cloud.service.BusinessStatus;
import com.kevin.cloud.message.api.CloudMessageService;
import com.kevin.cloud.service.feign.UserServiceFeign;
import com.kevin.cloud.service.dto.LoginParam;
import com.kevin.cloud.provider.api.UserService;
import com.kevin.cloud.provider.domain.UmsAdmin;
import okhttp3.Response;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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


    @Reference(version = "1.0.0")
    private CloudMessageService cloudMessageService;


    @Value("${server.port}")
    private int port;
    @Reference(version = "1.0.0")
    private RedisTemplateService redisTemplateService;


    @Value("${base.config.oauth.address}")
    private String oauthAddress;


    @PostMapping(value = "login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam, HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        // 验证密码是否正确
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            throw new BusinessException(BusinessStatus.ADMIN_PASSWORD);
        }
        String url = "http://" + oauthAddress + "/oauth/token";
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
        // 用户登录成功，异步发送消息， 写日志
        sendAdminLoginLogByMessage(request);
        //将 用户信息保存在redis中
        redisTemplateService.setJson("userInfo", map, 30 * 60);

        return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.OK, "登录成功", result);
    }


    private void sendAdminLoginLogByMessage(HttpServletRequest request) {
        MessageCommonDto messageCommonDto = new MessageCommonDto();
        String ipAddr = UserAgentUtils.getIpAddr(request);
        IpInfo info = UserAgentUtils.getIpInfo(ipAddr);
        messageCommonDto.setAddress(info.getCity());
        messageCommonDto.setIp(ipAddr);
        messageCommonDto.setUserAgent(UserAgentUtils.getUserAgent(request).getBrowser().getName());
        cloudMessageService.sendAdminLoginLog(messageCommonDto);
    }


    @GetMapping(value = "info/{username}")
    @SentinelResource(value = "info", fallback = "infoFallback")
    public ResponseResult<UmsAdmin> info(@PathVariable String username) {
        if (StringUtils.isBlank(username)) {
            // 获取认证信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            username = authentication.getName();
        }
        UmsAdmin umsAdmin = userService.get(username);
        return new ResponseResult<UmsAdmin>(ResponseResult.CodeStatus.OK, "获取个人信息", umsAdmin);
    }


    @GetMapping(value = "infoByFeign")
    public ResponseResult<UmsAdminDto> infoByFeign() throws Exception {
        // 获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 获取个人信息
        //UmsAdmin umsAdmin = (UmsAdmin)redisTemplateService.get("userInfo", UmsAdmin.class);
        String jsonString = userServiceFeign.info(authentication.getName());
        UmsAdmin umsAdmin = MapperUtils.json2pojoByTree(jsonString, "data", UmsAdmin.class);

        UmsAdminDto umsAdminDto = new UmsAdminDto();
        if (umsAdmin.getUsername() == null) {
            return new ResponseResult<UmsAdminDto>(ResponseResult.CodeStatus.ILLEGAL_REQUEST, "服务发生了熔断", umsAdminDto);
        }
        BeanUtils.copyProperties(umsAdmin, umsAdminDto);
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        List<String> roleCodes = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            roleCodes.add(grantedAuthority.getAuthority());
        }
        umsAdminDto.setRoleUserDtoList(roleCodes);
        // 如果触发熔断则返回熔断结果
        if (umsAdmin == null) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "", umsAdmin);
        }
        return new ResponseResult<UmsAdminDto>(ResponseResult.CodeStatus.OK, "通过feign获取个人信息", umsAdminDto);
    }


    @Autowired
    private TokenStore tokenStore;

    /**
     * 注销
     *
     * @return {@link ResponseResult}
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        // 获取 token
        String token = request.getParameter("access_token");
        // 删除 token 以注销
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        // 删除redis中的用户信息
        redisTemplateService.del("userInfo");
        return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "用户已注销");
    }

}
