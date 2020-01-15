package com.kevin.cloud.provider.service;

import com.kevin.cloud.commons.dto.RoleUserDto;
import com.kevin.cloud.provider.api.RoleService;
import com.kevin.cloud.provider.mapper.RoleMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: vue-blog-backend
 * @description: 角色服务
 * @author: kevin
 * @create: 2020-01-15 11:47
 **/

@Service(version = "1.0.0")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<RoleUserDto> getRoleByUserId(long userId) {
      return   roleMapper.getRoleByUserId(userId);
    }
}
