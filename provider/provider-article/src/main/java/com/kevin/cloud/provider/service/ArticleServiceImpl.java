package com.kevin.cloud.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.article.vo.ArticleVo;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.utils.BaseServiceUtils;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.provider.mapper.SiArticleMapper;
import com.kevin.cloud.provider.service.fallback.ArticleServiceDubboFallBack;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-15 14:22
 **/
@Service(version = "1.0.0")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private SiArticleMapper siArticleMapper;

    @SentinelResource(value = "articleList", fallback = "articleListFallBack", fallbackClass = ArticleServiceDubboFallBack.class)
    @Override
    public FallBackResult articleList(ArticleVo articleVo) {
        FallBackResult fallBackResult = new FallBackResult();
        PageHelper.startPage(articleVo.getPageNum(), articleVo.getPageSize());
        Example example = new Example(SiArticle.class);
        example.setOrderByClause("create_date DESC");
        List<ArticleDto> siArticles = siArticleMapper.queryArticleList(articleVo);
        PageInfo pageInfo = new PageInfo(siArticles);
        PageResult pageResult = BaseServiceUtils.buildPageResult(pageInfo);
        fallBackResult.setData(pageResult);
        return fallBackResult;
    }

    @Override
    public ArticleDto viewArticleById(Long articleId) {
        ArticleDto articleDto = new ArticleDto();
        SiArticle siArticle = siArticleMapper.selectByPrimaryKey(articleId);
        BeanUtils.copyProperties(siArticle, articleDto);
        return articleDto;
    }

    @Override
    public int saveArticle(SiArticle siArticle) {
        Example example = new Example(SiArticle.class);
        siArticle.setUpdateDate(new Date());
        example.createCriteria().andEqualTo("id", siArticle.getId());
        return siArticleMapper.updateByExampleSelective(siArticle, example);
    }

    @Override
    public int deleteIdArr(List<Long> idArr) {
        Example example = new Example(SiArticle.class);
        example.createCriteria().andIn("id", idArr);
        return siArticleMapper.deleteByExample(example);
    }

    @Override
    public int insert(SiArticle siArticle) {
        siArticle.setCreateDate(new Date());
        return siArticleMapper.insertSelective(siArticle);
    }


}
