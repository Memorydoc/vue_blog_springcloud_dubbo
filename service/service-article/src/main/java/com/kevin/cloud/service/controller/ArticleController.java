package com.kevin.cloud.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.article.vo.ArticleVo;
import com.kevin.cloud.commons.platform.dto.ESParamDto;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.api.ESService;
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

import java.util.Random;
import java.util.UUID;

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

        return new ResponseResult(ResponseResult.CodeStatus.OK, "修改成功", null);
    }

    /**
     * 删除文章
     */
    @PostMapping("deleteArticle")
    public ResponseResult deleteArticle(@RequestBody ArticleDto articleDto){
        int i = articleService.deleteIdArr(articleDto.getDeleteIdArr());
        return  new ResponseResult(ResponseResult.CodeStatus.OK, "删除成功", null);
    }


    /**
     * 添加文章
     */
    @PostMapping("addArticle")
    public ResponseResult addArticle(@RequestBody ArticleVo articleVo) throws Exception {
        SiArticle siArticle = new SiArticle();
        BeanUtils.copyProperties(articleVo, siArticle);
        siArticle.setId(System.currentTimeMillis());
        int i = articleService.insert(siArticle);
        if(i >0){
            System.out.println("插入数据库成功");
        }else{
            System.out.println("插入数据库失败");
        }
        //将 数据添加到es中
        //String jsonString = MapperUtils.obj2jsonIgnoreNull(siArticle);
        // 将文章保存到es中 方便全文搜索
        esService.addData(JSONObject.parseObject(siArticle.toString()), "article", "item");

        return  new ResponseResult(ResponseResult.CodeStatus.OK, "文章添加成功", null);
    }

    //分页查询文章, 从ES 中查询数据
    @PostMapping("queryArticleFromEsByPage")
    public ResponseResult queryArticleFromEsByPage(@RequestBody ArticleVo articleVo){
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
        PageResult result= (PageResult)esService.search(esParamDto);
        return  new ResponseResult(ResponseResult.CodeStatus.OK, "ES文章分页查询成功", result);
    }
    //分页查询文章, 从ES 中查询数据
    /*@PostMapping("queryArticleFromEsByPageByClient")
    public ResponseResult queryArticleFromEsByPageByClient(@RequestBody ArticleVo articleVo){
        PageResult result= esService.searchDataPage(articleVo.getKeyword(), articleVo.getPageNum(), articleVo.getPageSize());
        return  new ResponseResult(ResponseResult.CodeStatus.OK, "ES文章分页查询成功", result);
    }*/


}
