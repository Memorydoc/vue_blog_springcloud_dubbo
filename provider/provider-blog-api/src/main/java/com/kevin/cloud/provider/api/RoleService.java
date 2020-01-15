package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.RoleUserDto;
import com.kevin.cloud.provider.domain.Role;

import java.util.List;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-15 11:48
 **/
public interface RoleService {


    public List<RoleUserDto> getRoleByUserId(long userId);
}
