package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.provider.domain.SiArticle;

import java.util.List;


/**
 * @program: vue-blog-backend
 * @description: 文章服务
 * @author: kevin
 * @create: 2020-01-15 14:21
 **/
public interface ArticleService {

    public FallBackResult articleList(QueryPageParam queryPageParam);

    public ArticleDto viewArticleById(Long articleId);

    //修改
    public int saveArticle(SiArticle siArticled);

    public int deleteIdArr(List<Long> idArr);



    // 新增文章
    public int insert(SiArticle siArticle);

}
