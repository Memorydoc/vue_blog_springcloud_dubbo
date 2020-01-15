package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.article.ArticleDto;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.QueryPageParam;
import com.kevin.cloud.provider.domain.SiArticle;


/**
 * @program: vue-blog-backend
 * @description: 文章服务
 * @author: kevin
 * @create: 2020-01-15 14:21
 **/
public interface ArticleService {

    public FallBackResult articleList(QueryPageParam queryPageParam);

    public ArticleDto viewArticleById(Long articleId);

    public int saveArticle(SiArticle siArticled);

}
