package com.kevin.cloud.user.api;

import com.kevin.cloud.commons.dto.user.dto.UmsAdminDto;
import com.kevin.cloud.commons.dto.user.vo.UmsAdminVo;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.user.domain.UmsAdmin;

import java.util.List;

public interface UserService {

    public String sayHello(String  username);

    int insert(UmsAdmin umsAdmin);

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return {@link UmsAdmin}
     */
    UmsAdmin get(String username);

    /**
     * 获取用户
     *
     * @param umsAdmin {@link UmsAdmin}
     * @return {@link UmsAdmin}
     */
    UmsAdmin get(UmsAdmin umsAdmin);

    PageResult userList(UmsAdminVo umsAdminVo);

    int deleteUser(UmsAdminVo umsAdminVo);

    int editUser(UmsAdminVo umsAdminVo);

    UmsAdminDto getCurrentUser();

}
