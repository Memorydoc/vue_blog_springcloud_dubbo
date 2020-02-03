package com.kevin.cloud.provider.service;

import com.google.common.collect.Lists;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.blog.dto.TypeViewDto;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.api.BlogService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.provider.domain.SiColumn;
import com.kevin.cloud.provider.mapper.SiColumnMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.service
 * @ClassName: BlogServiceImpl
 * @Author: kevin
 * @Description: 博客统一服务接口
 * @Date: 2020/2/3 16:50
 * @Version: 1.0
 */
@Service(version = "1.0.0")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private SiColumnMapper siColumnMapper;


    @Reference(version = "1.0.0")
    private ArticleService articleService;


    @Override
    public List<TypeViewDto> initTypesData() {
        Example exampleType = new Example(SiColumn.class);
        List<TypeViewDto> listTypeViews = Lists.newArrayList();
        List<SiColumn> siColumns = siColumnMapper.selectByExample(exampleType);
        List<SiArticle> siArticles = articleService.selectLists();
        List<ArticleDto> listArticleDtos = Lists.newArrayList();
        siArticles.forEach(x ->{
            ArticleDto articleDto = new ArticleDto();
            BeanUtils.copyProperties(x, articleDto);
            listArticleDtos.add(articleDto);
        });
        Map<Long, List<ArticleDto>> collect = listArticleDtos.stream().collect(Collectors.groupingBy(ArticleDto::getCategory));
        siColumns.forEach(x ->{
            TypeViewDto typeViewDto = new TypeViewDto();
            BeanUtils.copyProperties(x, typeViewDto);
            typeViewDto.setArticleDtoList(collect.get(x.getId()));
            listTypeViews.add(typeViewDto);
        });
        return listTypeViews;
    }
}
