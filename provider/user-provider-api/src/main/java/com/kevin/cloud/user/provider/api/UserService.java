package com.kevin.cloud.user.provider.api;

import com.kevin.cloud.user.provider.api.domain.UmsAdmin;

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

}
