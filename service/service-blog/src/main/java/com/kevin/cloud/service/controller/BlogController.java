package com.kevin.cloud.service.controller;

import com.kevin.cloud.commons.dto.article.dto.SiFinkDto;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.provider.api.SiFinkService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ResponseCache;
import java.util.List;

/**
 * @program: vue-blog-backend
 * @description: 博客公共模块接口
 * @author: kevin
 * @create: 2020-01-15 11:25
 **/
@RequestMapping("blog")
@RestController
public class BlogController {


    @Reference(version = "1.0.0")
    private SiFinkService siFinkService;

    /**
     * 加载友情链接
     */
    @GetMapping("front/initLinks")
    public ResponseResult initLinks() {
        return new ResponseResult(ResponseResult.CodeStatus.OK, "", siFinkService.initLinks());
    }
}
