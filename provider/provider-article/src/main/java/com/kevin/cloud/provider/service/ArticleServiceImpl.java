package com.kevin.cloud.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.article.dto.SiColumnDto;
import com.kevin.cloud.commons.dto.article.dto.SiFinkDto;
import com.kevin.cloud.commons.dto.article.vo.ArticleVo;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.utils.BaseServiceUtils;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.provider.mapper.SiArticleMapper;
import com.kevin.cloud.provider.service.fallback.ArticleServiceDubboFallBack;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<ArticleDto> initTuijian() {
        Example example = new Example(SiArticle.class);
        example.createCriteria().andEqualTo("tuijian", 1);
        List<ArticleDto> list = Lists.newArrayList();
        List<SiArticle> siArticles = siArticleMapper.selectByExample(example);
        siArticles.forEach(x -> {
            ArticleDto articleDto = new ArticleDto();
            BeanUtils.copyProperties(x, articleDto);
            list.add(articleDto);
        });
        return list;
    }

    @Override
    public List<SiColumnDto> tuijianTags() {
        return siArticleMapper.tuijianTags();
    }

    @Override
    public List<ArticleDto> loadBefore(String esId) {
        Example example = new Example(SiArticle.class);
        example.createCriteria().andEqualTo("esId", esId);
        SiArticle siArticle = siArticleMapper.selectByExample(example).get(0);
        return siArticleMapper.loadBefore(siArticle.getCreateDate());
    }

    @Override
    public List<ArticleDto> loadAfter(String esId) {
        Example example = new Example(SiArticle.class);
        example.createCriteria().andEqualTo("esId", esId);
        SiArticle siArticle = siArticleMapper.selectByExample(example).get(0);
        return siArticleMapper.loadAfter(siArticle.getCreateDate());
    }

    @Override
    public List<SiArticle> selectLists() {
        Example example = new Example(SiArticle.class);
        return siArticleMapper.selectByExample(example);

    }

    @Override
    public PageResult initTimesData(ArticleVo articleVo) {
        PageHelper.startPage(articleVo.getPageNum(), articleVo.getPageSize());
        Example example = new Example(SiArticle.class);
        example.setOrderByClause("create_date DESC");
        List<SiArticle> siArticles = siArticleMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(siArticles);
        PageResult pageResult = BaseServiceUtils.buildPageResult(pageInfo);
        return pageResult;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArticleDto doLikeByEsId(String esId) {
        int update = 0;
        try {
            String doLikeSql = "UPDATE si_article SET liks = liks + 1 WHERE es_id = ?";
            update = jdbcTemplate.update(doLikeSql, esId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getArticle(update);
    }

    @Override
    public ArticleDto updateWgCount(ArticleVo articleVo) {
        int update = 0;
        try {
            String wgSql = "UPDATE si_article SET wgrs = wgrs + 1 WHERE es_id = ?";
            update = jdbcTemplate.update(wgSql, articleVo.getEsId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getArticle(update);
    }

    @Override
    public ArticleDto selectClickTop() {
        String sql = "SELECT  * FROM si_article ORDER BY wgrs DESC LIMIT 1 ";
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(sql);
        ArticleDto articleDto = MapperUtils.map2pojo(stringObjectMap, ArticleDto.class);
        return articleDto;
    }

    @Override
    public List<SiArticle> loadCurrentTuijianData(String esId) {
        SiArticle siArticle = new SiArticle();
        Example example = new Example(SiArticle.class);
        example.createCriteria().andEqualTo("esId", esId);
        List<SiArticle> siArticles = siArticleMapper.selectByExample(example);
        if (siArticles.size() > 0) {
            siArticle = siArticles.get(0);
        } else {
            return null;
        }
        Example exampleResult = new Example(SiArticle.class);
        exampleResult.setOrderByClause("wgrs desc ");
        exampleResult.createCriteria().andEqualTo("category", siArticle.getCategory());
        List<SiArticle> resultList = siArticleMapper.selectByExample(exampleResult);
        if (resultList.size() >= 5) {
            resultList = resultList.subList(0, 5);
        }
        return resultList;
    }

    @Override
    public List<SiArticle> loadClickTops() {
        Example example = new Example(SiArticle.class);
        example.setOrderByClause("wgrs desc");
        List<SiArticle> articles = siArticleMapper.selectByExample(example);
        if(articles.size() >= 5){
            articles = articles.subList(0,5);
        }
        return articles ;
    }

    @Override
    public List<SiArticle> loadRelativeArticles(String esId) {
        Example example = new Example(SiArticle.class);
        example.createCriteria().andEqualTo("esId", esId);
        List<SiArticle> articles = siArticleMapper.selectByExample(example);
        if(articles.size() >= 10){
            articles = articles.subList(0, 10);
        }
        return articles;
    }

    private ArticleDto getArticle(int update) {
        if (update != 0) {
            Example example = new Example(SiArticle.class);
            SiArticle siArticle = null;
            List<SiArticle> siArticles = siArticleMapper.selectByExample(example);
            if (siArticles.size() != 0) {
                siArticle = siArticles.get(0);
            }
            ArticleDto articleDto = new ArticleDto();
            BeanUtils.copyProperties(siArticle, articleDto);
            return articleDto;
        }
        return null;
    }

}
