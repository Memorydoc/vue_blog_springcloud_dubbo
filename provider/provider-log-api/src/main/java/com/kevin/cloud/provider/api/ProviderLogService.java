package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.QueryPageParam;
import com.kevin.cloud.provider.domain.UmsAdminLoginLog;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-12 22:48
 **/
public interface  ProviderLogService {


    //分页查询用户登录日志
    public FallBackResult queryUserLoginLogByPage(QueryPageParam queryPageParam);

    public FallBackResult insert(UmsAdminLoginLog umsAdminLoginLog);

}
