package com.kevin.cloud.service.controller;

import com.kevin.cloud.commons.dto.article.ArticleDto;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.dto.QueryPageParam;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.user.api.UserService;
import com.kevin.cloud.user.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: vue-blog-backend
 * @description: 文章接口服务
 * @author: kevin
 * @create: 2020-01-15 14:14
 **/

@RequestMapping("article")
@RestController
public class ArticleController {

    @Reference(version = "1.0.0")
    private ArticleService articleService;


    @PostMapping("list")
    public ResponseResult<ArticleDto> list(@RequestBody QueryPageParam queryPageParam) {
        FallBackResult fallBackResult = articleService.articleList(queryPageParam);
        if (fallBackResult.isStatus()) {
            PageResult pageResult = (PageResult) fallBackResult.getData();
            return new ResponseResult(ResponseResult.CodeStatus.OK, "文章列表查询成功", pageResult);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "文章列表查询失败", null);
        }
    }

    @GetMapping("viewArticleById/{id}")
    public ResponseResult viewArticleById(@PathVariable("id") Long id) {

        ArticleDto articleDto = articleService.viewArticleById(id);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "成功", articleDto);
    }

    @Reference(version = "1.0.0")
    private UserService userService;

    @PostMapping("saveArticle")
    public ResponseResult saveArticle(@RequestBody ArticleDto articleDto) {
        SiArticle siArticle = new SiArticle();
        BeanUtils.copyProperties(articleDto, siArticle);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UmsAdmin umsAdmin = userService.get(authentication.getName());
        siArticle.setUpdateBy(umsAdmin.getId());
        int i = articleService.saveArticle(siArticle);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "修改成功", null);
    }

}
