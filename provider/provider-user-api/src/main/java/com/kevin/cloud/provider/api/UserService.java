package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.user.dto.UmsAdminDto;
import com.kevin.cloud.commons.dto.user.vo.UmsAdminVo;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.provider.domain.UmsAdmin;

import java.util.Map;

public interface UserService {

    public String sayHello(String username);

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

    Map<String, Object> customerStatus(UmsAdminVo umsAdminVo);

    UmsAdmin doCustomerRegister(UmsAdminVo umsAdminVo);

    Map<String, Object> queryUserByPhone(Long phone);

    boolean judgePhoneUserIsExist(String phone);

    boolean checkEmailAddress(String phone);
}
