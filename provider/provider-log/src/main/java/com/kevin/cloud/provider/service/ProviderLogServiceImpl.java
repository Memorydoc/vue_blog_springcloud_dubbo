package com.kevin.cloud.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.dto.QueryPageParam;
import com.kevin.cloud.commons.platform.utils.BaseServiceUtils;
import com.kevin.cloud.provider.api.ProviderLogService;
import com.kevin.cloud.provider.domain.UmsAdminLoginLog;
import com.kevin.cloud.provider.mapper.UmsAdminLoginLogMapper;
import com.kevin.cloud.provider.service.fallback.ProviderLogServiceImplFallback;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-12 22:48
 **/
@Service(version = "1.0.0")
public class ProviderLogServiceImpl implements ProviderLogService {
    @Autowired
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;


    @SentinelResource(value = "sendAdminLoginLog", fallback = "queryUserLoginLogByPageFallBack", fallbackClass = ProviderLogServiceImplFallback.class)
    @Override
    public FallBackResult queryUserLoginLogByPage(QueryPageParam queryPageParam) {
        FallBackResult fallBackResult = new FallBackResult();
        PageHelper.startPage(queryPageParam.getPageNum(), queryPageParam.getPageSize());
        Example example = new Example(UmsAdminLoginLog.class);
        List<UmsAdminLoginLog> umsAdminLoginLogs = umsAdminLoginLogMapper.selectByExample(example);
        PageInfo pageInfo =  new PageInfo(umsAdminLoginLogs);
        PageResult pageResult = BaseServiceUtils.buildPageResult(pageInfo);
        fallBackResult.setData(pageResult);
        return fallBackResult;
    }

    @SentinelResource(value = "sendAdminLoginLog", fallback = "sendUmsAdminLoginLogFallBack", fallbackClass = ProviderLogServiceImplFallback.class)
    @Override
    public FallBackResult insert(UmsAdminLoginLog umsAdminLoginLog) {
        FallBackResult fallBackResult = new FallBackResult();
        int insert = umsAdminLoginLogMapper.insertSelective(umsAdminLoginLog);
        if (insert > 0) {
            fallBackResult.setStatus(true);
            return fallBackResult;
        }
        fallBackResult.setStatus(false);
        return fallBackResult;
    }
}
