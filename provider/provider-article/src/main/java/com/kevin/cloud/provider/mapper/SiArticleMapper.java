package com.kevin.cloud.provider.mapper;

import com.kevin.cloud.commons.dto.article.dto.ArticleDto;import com.kevin.cloud.commons.dto.article.dto.SiColumnDto;import com.kevin.cloud.commons.dto.article.vo.ArticleVo;import com.kevin.cloud.provider.domain.SiArticle;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface SiArticleMapper extends Mapper<SiArticle> {
    List<ArticleDto> queryArticleList(ArticleVo articleVo);

    List<SiColumnDto> tuijianTags();

    List<ArticleDto> loadBeforeAndBack(@Param("createDate") Date createDate);

    List<ArticleDto> loadBefore(Date createDate);

    List<ArticleDto> loadAfter(Date createDate);
}