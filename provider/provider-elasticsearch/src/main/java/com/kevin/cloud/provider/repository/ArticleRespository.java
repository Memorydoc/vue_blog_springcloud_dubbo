package com.kevin.cloud.provider.repository;

import com.kevin.cloud.provider.pojo.ArticleESDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: vue-blog-backend
 * @description: 文章服务搜索接口
 * @author: kevin
 * @create: 2020-01-18 12:40
 **/
@Component
public interface  ArticleRespository extends ElasticsearchRepository<ArticleESDto, Long> {
    List<ArticleESDto> findArticleESDtoByMcOrContent(String keyWord);

}
