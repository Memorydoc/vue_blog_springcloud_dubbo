package com.kevin.cloud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: vue-blog-backend
 * @description: 无效token 异常重写
 * @author: kevin
 * @create: 2020-01-24 00:14
 **/
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Map<String, Object> map = new HashMap<String, Object>();
        Throwable cause = authException.getCause();
        if(cause instanceof InvalidTokenException) {
            map.put("code", BusinessStatus.ILLEGAL_TOKEN.getCode());// 非法令牌
            map.put("message", BusinessStatus.ILLEGAL_TOKEN.getMessage());// 自定义登录超时
        }else{
            map.put("code", HttpServletResponse.SC_UNAUTHORIZED);//401
            map.put("message", "访问此资源需要完整的身份验证");
        }
        map.put("data", authException.getMessage());
        map.put("success", false);
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(new Date().getTime()));
        response.setContentType("application/json");
        // 这里为了 配合前台vue， 也是返回200 状态，因为vue-element-admin 的requestjs中使用的是code判断超时
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}