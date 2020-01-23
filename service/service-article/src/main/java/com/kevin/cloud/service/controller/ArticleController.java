package com.kevin.cloud.service.controller;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.article.vo.ArticleVo;
import com.kevin.cloud.commons.platform.dto.ESParamDto;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.commons.utils.CommonUtils;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.api.ESService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.service.IdGenerator;
import com.kevin.cloud.service.feign.UserServiceFeign;
import com.kevin.cloud.user.api.UserService;
import com.kevin.cloud.user.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    @Reference(version = "1.0.0")
    private ESService esService;

    @PostMapping("saveArticle")
    public ResponseResult saveArticle(@RequestBody ArticleDto articleDto) throws Exception {
        SiArticle siArticle = new SiArticle();
        BeanUtils.copyProperties(articleDto, siArticle);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UmsAdmin umsAdmin = userService.get(authentication.getName());
        siArticle.setUpdateBy(umsAdmin.getId());
        //先将文章保存到数据库
        int i = articleService.saveArticle(siArticle);
        esService.updateDataById(CommonUtils.beanToJSONObject(siArticle), "article", "item", siArticle.getId().toString());
        return new ResponseResult(ResponseResult.CodeStatus.OK, "修改成功", null);
    }

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 删除文章
     */
    @PostMapping("deleteArticle")
    public ResponseResult deleteArticle(@RequestBody ArticleDto articleDto) {
        List<String> esList = Lists.newArrayList();
        int i = articleService.deleteIdArr(articleDto.getDeleteIdArr());
        // 刪除es 中的文章
        articleDto.getDeleteIdArr().forEach(x -> {
            esList.add(x.toString());
        });
        esService.deleteDataByIdMany("article", "item", esList);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "删除成功", null);
    }


    @Resource
    private UserServiceFeign userServiceFeign;

    /**
     * 添加文章
     */
    @PostMapping("addArticle")
    public ResponseResult addArticle(@RequestBody ArticleVo articleVo) throws Exception {
        SiArticle siArticle = new SiArticle();
        BeanUtils.copyProperties(articleVo, siArticle);
        siArticle.setId(idGenerator.nextLid());
        siArticle.setCreateBy(userServiceFeign.getCurrentUser().getId());
        int i = articleService.insert(siArticle);
        if (i > 0) {
            System.out.println("插入数据库成功");
        } else {
            System.out.println("插入数据库失败");
        }

        // 将文章保存到es中 方便全文搜索
        String esId = esService.addDataId(CommonUtils.beanToJSONObject(siArticle), "article", "item", siArticle.getId() + "");

        return new ResponseResult(ResponseResult.CodeStatus.OK, "文章添加成功", esId);
    }

    //分页查询文章, 从ES 中查询数据
    @PostMapping("queryArticleFromEsByPage")
    public ResponseResult queryArticleFromEsByPage(@RequestBody ArticleVo articleVo) {
        ESParamDto esParamDto = new ESParamDto();
        esParamDto.setHightLight(true);
        esParamDto.setPage(true);
        esParamDto.setPageNum(articleVo.getPageNum());
        esParamDto.setPageSize(articleVo.getPageSize());
        esParamDto.setKeyword(articleVo.getKeyword());
        esParamDto.setMatchField("mc,content");
        esParamDto.setIndex("article");
        esParamDto.setType("item");
        esParamDto.setHightLightField("mc,content");
        PageResult result = (PageResult) esService.search(esParamDto);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "ES文章分页查询成功", result);
    }
}
