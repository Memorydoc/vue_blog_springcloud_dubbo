package com.kevin.cloud.provider.mapper;

import com.kevin.cloud.commons.dto.RoleUserDto;import com.kevin.cloud.provider.domain.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface RoleMapper extends MyMapper<Role> {
    List<RoleUserDto> getRoleByUserId(@Param("userId") Long userId);
}