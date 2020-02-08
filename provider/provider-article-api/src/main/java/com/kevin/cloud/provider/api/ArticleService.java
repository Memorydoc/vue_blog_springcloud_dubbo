package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.article.dto.SiColumnDto;
import com.kevin.cloud.commons.dto.article.dto.SiFinkDto;
import com.kevin.cloud.commons.dto.article.vo.ArticleVo;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.provider.domain.SiArticle;

import java.util.List;


/**
 * @program: vue-blog-backend
 * @description: 文章服务
 * @author: kevin
 * @create: 2020-01-15 14:21
 **/
public interface ArticleService {

    public FallBackResult articleList(ArticleVo articleVo);

    public ArticleDto viewArticleById(Long articleId);

    //修改
    public SiArticle saveArticle(SiArticle siArticled);

    public List<String> deleteIdArr(List<Long> idArr);



    // 新增文章
    public int insert(SiArticle siArticle);


    /**
     * 加载特别推荐数据
     */
    public List<ArticleDto> initTuijian();

    public List<SiColumnDto> tuijianTags();

    List<ArticleDto> loadBefore(String esId);

    List<ArticleDto> loadAfter(String esId);

    List<SiArticle> selectLists();

    PageResult initTimesData(ArticleVo articleVo);

    ArticleDto doLikeByEsId(String esId);

    ArticleDto updateWgCount(ArticleVo articleVo);

    ArticleDto selectClickTop();

    List<SiArticle> loadCurrentTuijianData(String esId);

    List<SiArticle> loadClickTops();

    List<SiArticle> loadRelativeArticles(String esId);
}
