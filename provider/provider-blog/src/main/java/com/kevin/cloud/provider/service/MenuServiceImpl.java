package com.kevin.cloud.provider.service;

import com.kevin.cloud.provider.api.MenuService;
import com.kevin.cloud.provider.domain.Menu;
import com.kevin.cloud.provider.mapper.MenuMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @program: vue-blog-backend
 * @description: 菜单服务类
 * @author: kevin
 * @create: 2020-01-14 23:41
 **/
@Service(version = "1.0.0")
public class MenuServiceImpl  implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> finlAllMenuByList() {
        List<Menu> menus = menuMapper.selectByExample(new Example(Menu.class));
        return  menus;
    }
}
