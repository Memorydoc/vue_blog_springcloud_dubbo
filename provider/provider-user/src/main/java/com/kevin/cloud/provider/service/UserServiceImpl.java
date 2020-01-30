package com.kevin.cloud.provider.service;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.cloud.commons.dto.user.dto.UmsAdminDto;
import com.kevin.cloud.commons.dto.user.vo.UmsAdminVo;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.utils.BaseServiceUtils;
import com.kevin.cloud.user.api.UserService;
import com.kevin.cloud.user.domain.UmsAdmin;
import com.kevin.cloud.provider.mapper.UmsAdminMapper;
import com.kevin.cloud.provider.service.fallback.UserServiceImplFallback;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Resource
    private UmsAdminMapper umsAdminMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String sayHello(String username) {
        return "我是dubbo 服务 欢迎您 " + username;
    }

    @Override
    public int insert(UmsAdmin umsAdmin) {
        initUmsAdmin(umsAdmin);
        return umsAdminMapper.insert(umsAdmin);
    }

    /**
     * 初始化用户对象
     *
     * @param umsAdmin {@link UmsAdmin}
     */
    private void initUmsAdmin(UmsAdmin umsAdmin) {
        // 初始化创建时间
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setLoginTime(new Date());

        // 初始化状态
        if (umsAdmin.getStatus() == null) {
            umsAdmin.setStatus(0);
        }

        // 密码加密
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
    }

    @SentinelResource(value = "getByUserName", fallback = "getByUsernameFallback", fallbackClass = UserServiceImplFallback.class)
    @Override
    public UmsAdmin get(String username) {
        Example example = new Example(UmsAdmin.class);
        example.createCriteria().andEqualTo("username", username);
        return umsAdminMapper.selectOneByExample(example);
    }

    @Override
    public UmsAdmin get(UmsAdmin umsAdmin) {
        return umsAdminMapper.selectOne(umsAdmin);
    }

    @Override
    public PageResult userList(UmsAdminVo umsAdminVo) {
        PageHelper.startPage(umsAdminVo.getPageNum(), umsAdminVo.getPageSize());
        Example example = new Example(UmsAdmin.class);
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(umsAdmins);
        PageResult pageResult = BaseServiceUtils.buildPageResult(pageInfo);
        return pageResult;
    }

    @Override
    public int deleteUser(UmsAdminVo umsAdminVo) {
        Example example = new Example(UmsAdmin.class);
        example.createCriteria().andIn("id", umsAdminVo.getDeleteUserlist());
        return umsAdminMapper.deleteByExample(example);
    }

    @Override
    public int editUser(UmsAdminVo umsAdminVo) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminVo, umsAdmin);
        return umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
    }

    /**
     * 获取当前登录人
     * @return
     */
    @Override
    public UmsAdminDto getCurrentUser() {

        return null;
    }

}
