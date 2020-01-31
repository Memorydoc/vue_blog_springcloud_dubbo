package com.kevin.cloud.provider.mapper;

import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.article.vo.ArticleVo;
import com.kevin.cloud.provider.domain.SiArticle;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SiArticleMapper extends Mapper<SiArticle> {
    List<ArticleDto> queryArticleList(ArticleVo articleVo);
}