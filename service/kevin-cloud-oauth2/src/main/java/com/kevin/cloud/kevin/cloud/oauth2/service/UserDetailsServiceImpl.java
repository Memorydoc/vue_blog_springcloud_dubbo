package com.kevin.cloud.kevin.cloud.oauth2.service;

import com.google.common.collect.Lists;
import com.kevin.cloud.user.api.UserService;
import com.kevin.cloud.user.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-05 16:20
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference(version = "1.0.0")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 查询用户
        UmsAdmin umsAdmin = userService.get(s);

        // 默认所有用户拥有 USER 权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);

        // 用户存在
        if (umsAdmin != null) {
            return new User(umsAdmin.getUsername(), umsAdmin.getPassword(), grantedAuthorities);
        }

        // 用户不存在
        else {
            return null;
        }
    }
}