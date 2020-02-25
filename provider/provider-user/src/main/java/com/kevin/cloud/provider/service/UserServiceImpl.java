package com.kevin.cloud.provider.service;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.cloud.commons.dto.user.dto.UmsAdminDto;
import com.kevin.cloud.commons.dto.user.vo.UmsAdminVo;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.utils.BaseServiceUtils;
import com.kevin.cloud.provider.IdProviderGenerator;
import com.kevin.cloud.provider.mapper.UmsAdminMapper;
import com.kevin.cloud.provider.service.fallback.UserServiceImplFallback;
import com.kevin.cloud.provider.api.UserService;
import com.kevin.cloud.provider.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Override
    public Map<String, Object> customerStatus(UmsAdminVo umsAdminVo) {
        String resultMsg = "";
        UmsAdmin umsADmin = null;
        Map resultMap = new HashMap();
        Example example = new Example(UmsAdmin.class);
        example.createCriteria().andEqualTo("email", umsAdminVo.getEmail())
                .andEqualTo("isCustomer", 1);
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(example);
        if(umsAdmins.size() != 0){ //说明用户存在，继续验证密码是否正确
            umsADmin = umsAdmins.get(0);
            boolean matches = passwordEncoder.matches(umsAdminVo.getPassword(), umsADmin.getPassword());
            if(matches){
                UmsAdminDto umsAdminDto = new UmsAdminDto();
                BeanUtils.copyProperties(umsADmin, umsAdminDto);
                resultMap.put("resultMsg", "登录成功");
                resultMap.put("customer", umsAdminDto);
                return  resultMap;
            }else{
                resultMap.put("resultMsg", "密码错误");
                resultMap.put("customer", null);
                return  resultMap;
            }
        }
        resultMap.put("resultMsg", "用户不存在,请先注册");
        resultMap.put("customer", null);
        return  resultMap;
    }
    @Autowired
    private IdProviderGenerator idGenerator;

    @Override
    public UmsAdmin doCustomerRegister(UmsAdminVo umsAdminVo) {
        Map resultMap = new HashMap();
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminVo, umsAdmin);
        umsAdmin.setId(idGenerator.nextLid());
        umsAdmin.setIsCustomer(1); // 设置 用户为游客
        umsAdmin.setCreateTime(new Date());
        int i = umsAdminMapper.insertSelective(umsAdmin);
        if(i > 0){
            return  umsAdmin;
        }
        return null;
    }

    @Override
    public Map<String, Object> queryUserByPhone(Long phone) {
        Map resultMap = new HashMap();
        Example example = new Example(UmsAdmin.class);
        example.createCriteria().andEqualTo("phone", phone).andEqualTo("isCustomer", 1);
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(example);
        if(umsAdmins.size() > 0){
            UmsAdminDto umsAdminDto = new UmsAdminDto();
            BeanUtils.copyProperties(umsAdmins.get(0), umsAdminDto);
            resultMap.put("resultMsg", "登录成功");
            resultMap.put("customer", umsAdminDto);
            return resultMap;
        }else{
            resultMap.put("resultMsg", "用户不存在");
            resultMap.put("customer", null);
        }

        return resultMap;
    }

    @Override
    public boolean judgePhoneUserIsExist(String phone) {
        Example example = new Example(UmsAdmin.class);
        example.createCriteria().andEqualTo("phone", phone);
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(example);
        if(umsAdmins.size() > 0){
            return  true;
        }
        return false;
    }

    @Override
    public boolean checkEmailAddress(String email) {
        Example example = new Example(UmsAdmin.class);
        example.createCriteria().andEqualTo("email", email);
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(example);
        if(umsAdmins.size() == 0){
            return true;
        }else{
            return false;
        }
    }
}
