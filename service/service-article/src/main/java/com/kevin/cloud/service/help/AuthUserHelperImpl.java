package com.kevin.cloud.service.help;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-24 16:23
 **/

import com.kevin.cloud.commons.dto.user.dto.UmsAdminDto;
import com.kevin.cloud.user.api.UserService;
import com.kevin.cloud.user.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AuthUserHelperImpl {
    @Resource
    private HttpServletRequest request;

    @Reference(version = "1.0.0")
    private UserService userService;

    public String getCurrentUserName() {
        String result = getAuthentication().getName();
        if (Strings.isEmpty(result)) {
            throw new RuntimeException("failed to get user name from authentication");
        }
        return result;
    }


    public UmsAdminDto getCurrentUser() {
        Object user = getAuthentication().getPrincipal();
        if (user == null) {
            throw new RuntimeException("failed to get JwtUser from authentication");
        }
        UmsAdminDto umsAdminDto = new UmsAdminDto();
        UmsAdmin umsAdmin = userService.get(user.toString());
        BeanUtils.copyProperties(umsAdmin, umsAdminDto);
        return umsAdminDto;
    }

    private Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            throw new RuntimeException("security context is null");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("authentication failed");
        }
        return authentication;
    }

}