/*
package com.kevin.cloud.provider.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.kevin.cloud.commons.dto.QueryPageParam;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.search.SearchDto;
import com.kevin.cloud.commons.platform.dto.ESParamDto;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.utils.BaseServiceUtils;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.provider.api.ESService;
import com.kevin.cloud.provider.api.ESSpringDataService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.provider.pojo.ArticleESDto;
import com.kevin.cloud.provider.repository.ArticleRespository;
import com.kevin.cloud.provider.util.ElasticsearchUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

*/
/**
 * @program: vue-blog-backend
 * @description: 文章搜索服务
 * @author: kevin
 * @create: 2020-01-18 12:48
 **//*

@Service(version = "1.0.0")
public class ElasticsearchSpringDataService implements ESSpringDataService {
    private static  final  String INDEX_NAME = "article";
    private static  final  String TYPE_NAME= "item";


    @Autowired
    private ArticleRespository articleRespository;
    private static final String analysis = "ik_smart";


   */
/* @Override
    public SiArticle search(String keys, Pageable pageable) {


        //按标题进行搜索
        QueryBuilder queryBuilder = matchQuery("title", keys);

        //如果实体和数据的名称对应就会自动封装，pageable分页参数
        Iterable<SiArticle> listIt =  articleRespository.search(queryBuilder,pageable);

        //Iterable转list
        List<SiArticle> list= Lists.newArrayList(listIt);
        return null;
    }*//*



    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;    //es工具

    */
/**
     * 从es检索数据
     *
     * @param content        搜索关键字
     * @param queryPageParam 页信息
     * @return
     *//*

    public AggregatedPage<SiArticle> searchArticleByTemplate(String content, QueryPageParam queryPageParam) {
        Pageable pageable = PageRequest.of(queryPageParam.getPageNum(), queryPageParam.getPageSize());
        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(matchQuery("mc", content)).
                withQuery(matchQuery("content", content)).
                withHighlightFields(new HighlightBuilder.Field("mc").preTags(preTag).postTags(postTag),
                        new HighlightBuilder.Field("content").preTags(preTag).postTags(postTag)).build();
        searchQuery.setPageable(pageable);

        // 不需要高亮直接return ideas
        // AggregatedPage<Idea> ideas = elasticsearchTemplate.queryForPage(searchQuery, Idea.class);

        // 高亮字段
        AggregatedPage<SiArticle> articles = elasticsearchTemplate.queryForPage(searchQuery, SiArticle.class, new SearchResultMapper() {

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<SiArticle> chunk = new ArrayList<>();
                for (SearchHit searchHit : response.getHits()) {
                    if (response.getHits().getHits().length <= 0) {
                        return null;
                    }
                    SiArticle idea = new SiArticle();
                    //name or memoe
                    HighlightField ideaTitle = searchHit.getHighlightFields().get("mc");
                    if (ideaTitle != null) {
                        idea.setContent(ideaTitle.fragments()[0].toString());
                    }
                    HighlightField ideaContent = searchHit.getHighlightFields().get("content");
                    if (ideaContent != null) {
                        idea.setContent(ideaContent.fragments()[0].toString());
                    }
                    chunk.add(idea);
                }
                if (chunk.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) chunk);
                }
                return null;
            }
        });
        return articles;
    }

    @Override
    public PageResult searchByPage(String keyword, int pageNum, int pageSize) {
        // 这里有个坑， es中的pageNum 是从第0 页开始的
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";
        //先创建 QueryBuilder
        QueryBuilder queryBuilderMc = QueryBuilders.matchQuery("mc", keyword)
                .analyzer("ik_smart");
        QueryBuilder queryBuilderContent = QueryBuilders.matchQuery("content", keyword)
                .analyzer("ik_smart");
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(queryBuilderMc).should(queryBuilderContent);

        // 创建查询 SearchQuery
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withHighlightFields(new HighlightBuilder.Field("mc").preTags(preTag).postTags(postTag),
                        new HighlightBuilder.Field("content").preTags(preTag).postTags(postTag))
                .withPageable(pageable).
                        withQuery(queryBuilder).build();
        //进行查询
        Page<ArticleESDto> search = articleRespository.search(searchQuery);




        List<ArticleDto> list = Lists.newArrayList();
        for (ArticleESDto articleESDto : search) {
            ArticleDto  articleDto = new ArticleDto();
            BeanUtils.copyProperties(articleESDto, articleDto);
            list.add(articleDto);
        }
        PageInfo pageInfo = new PageInfo(list);
        PageResult pageResult = BaseServiceUtils.buildPageResult(pageInfo);
        return pageResult;
    }

    @Override
    public Object search(String keyword, String matchField, boolean isPage, boolean isHightLight) {
        return null;
    }

    public Object search(String keyword) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("cid", keyword);
        Iterable<ArticleESDto> search = articleRespository.search(matchQuery);
        List<SiArticle> siArticles = new ArrayList<>();
        for (ArticleESDto articleESDto : search) {
            SiArticle siArticle = new SiArticle();
            BeanUtils.copyProperties(articleESDto, siArticle);
            siArticles.add(siArticle);
        }
        return siArticles;
    }



    @Override
    public Object search(ESParamDto esParamDto) {
        QueryBuilder query = null;
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";
        if(esParamDto.getQueryBuilder() != null){
            query = (QueryBuilder)esParamDto.getQueryBuilder();
        }else{
            //先创建 QueryBuilder
            String matchField = esParamDto.getMatchField();
            if (StringUtils.isBlank(matchField)) {
                throw new RuntimeException("matchField is error ");
            }
            String[] split = matchField.split(",");
            for (String key : split) {
                //创建 QueryBuilder
                boolQueryBuilder = boolQueryBuilder.should(QueryBuilders.matchQuery(key, esParamDto.getKeyword())
                        .analyzer(analysis));
            }
            query = boolQueryBuilder; //最后 这个是查询
        }
        if(esParamDto.isPage() && !esParamDto.isHightLight()){//只分页不高 亮
            // 这里有个坑， es中的pageNum 是从第0 页开始的
            Pageable pageable = PageRequest.of(esParamDto.getPageNum() - 1, esParamDto.getPageSize());
            // 创建查询 SearchQuery
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withPageable(pageable).
                            withQuery(query).build();
            //进行查询
            Page<ArticleESDto> search = articleRespository.search(searchQuery);
            List<ArticleDto> list = Lists.newArrayList();
            for (ArticleESDto articleESDto : search) {
                ArticleDto  articleDto = new ArticleDto();
                BeanUtils.copyProperties(articleESDto, articleDto);
                list.add(articleDto);
            }
            PageInfo pageInfo = new PageInfo(list);
            PageResult pageResult = BaseServiceUtils.buildPageResult(pageInfo);
            return pageResult;
        }else if(!esParamDto.isPage() && esParamDto.isHightLight()){

            // 创建查询 SearchQuery
            SearchQuery searchQuery = new NativeSearchQueryBuilder().
                    withHighlightFields(new HighlightBuilder.Field("mc").preTags(preTag).postTags(postTag),
                            new HighlightBuilder.Field("content").preTags(preTag).postTags(postTag)).
                            withQuery(query).build();
            //进行查询
            Iterable<ArticleESDto> search = articleRespository.search(searchQuery);
            List<SiArticle> siArticles = Lists.newArrayList();
            for (ArticleESDto articleESDto : search) {
                SiArticle siArticle = new SiArticle();
                BeanUtils.copyProperties(articleESDto, siArticle);
                siArticles.add(siArticle);
            }
            return siArticles;
        }else if(esParamDto.isPage() && esParamDto.isHightLight()){
            Pageable pageable = PageRequest.of(esParamDto.getPageNum() - 1, esParamDto.getPageSize());
            // 创建查询 SearchQuery
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withPageable(pageable).
                            withQuery(query).build();
            //进行查询
            Page<ArticleESDto> search = articleRespository.search(searchQuery);
            List<ArticleDto> list = Lists.newArrayList();
            for (ArticleESDto articleESDto : search) {
                ArticleDto  articleDto = new ArticleDto();
                BeanUtils.copyProperties(articleESDto, articleDto);
                list.add(articleDto);
            }
            PageInfo pageInfo = new PageInfo(list);
            PageResult pageResult = BaseServiceUtils.buildPageResult(pageInfo);
            return pageResult;
        }



    }

    @Override
    public boolean createIndex(String index) {
         elasticsearchTemplate.createIndex(ArticleESDto.class);
        return elasticsearchTemplate.putMapping(ArticleESDto.class);
    }

    @Override
    public boolean deleteIndex(String index) {
       return elasticsearchTemplate.deleteIndex(index);
    }

    @Override
    public boolean deleteIndex(Class indexClass) {
        return elasticsearchTemplate.deleteIndex(indexClass);
    }

    @Override
    public Object addData(String jsonObject) throws Exception {
        ArticleESDto articleESDto = MapperUtils.json2pojoByFastJson(jsonObject, ArticleESDto.class);
        return articleRespository.save(articleESDto);
    }


    @Override
    public void deleteDataById(long id) {
        articleRespository.deleteById(id);
    }
    @Override
    public void deleteData(Class cls) throws Exception {
        ArticleESDto articleESDto =(ArticleESDto) cls.newInstance();
        articleRespository.delete(articleESDto);
    }

    @Override
    public void updateDataById(String json) throws Exception {
        ArticleESDto articleESDto = MapperUtils.json2pojoByFastJson(json, ArticleESDto.class);
        articleRespository.save(articleESDto);
    }

    @Override
    public Map<String, Object> searchDataById(String index, String type, String id, String fields) {
        return null;
    }
}
*/
