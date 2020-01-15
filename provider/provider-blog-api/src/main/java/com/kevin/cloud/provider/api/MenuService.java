package com.kevin.cloud.provider.api;


import com.kevin.cloud.provider.domain.Menu;

import java.util.List;

/**
 * @program: vue-blog-backend
 * @description: 博客管理模块菜单
 * @author: kevin
 * @create: 2020-01-14 20:47
 **/
public interface MenuService {

    public List<Menu> finlAllMenuByList();
}
