package com.kevin.cloud.provider.service;

import com.alibaba.fastjson.JSONObject;
import com.kevin.cloud.commons.dto.CommonConstant;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.platform.dto.ESParamDto;
import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.provider.api.ESService;
import com.kevin.cloud.provider.pojo.BaseESDto;
import com.kevin.cloud.provider.pojo.ESManagement;
import com.kevin.cloud.provider.util.ElasticsearchUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @program: vue-blog-backend
 * @description: Elasticsearch 统一接口服务
 * @author: kevin
 * @create: 2020-01-22 20:44
 **/
@Service(version = "1.0.0")
public class ElasticsearchClientService implements ESService {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;    //es工具

    @Autowired
    private ESManagement esManagement;

    @PostConstruct
    public void init() {
        Map<String, BaseESDto> processorMap = esManagement.getProcessorMap();
        for (Map.Entry<String, BaseESDto> entry : processorMap.entrySet()) {
            String indexName = entry.getValue().getClass().getAnnotation(Document.class).indexName();
            boolean indexExist = ElasticsearchUtil.isIndexExist(indexName);
            if (indexExist) {
                // ElasticsearchUtil.deleteIndex(indexName);
            } else { //使用spring-data 的方式，比较简便
                // 创建索引
                elasticsearchTemplate.createIndex(entry.getValue().getClass());
                //创建mapping
                elasticsearchTemplate.putMapping(entry.getValue().getClass());
            }
        }
    }

    @Override
    public Object search(ESParamDto esParamDto) {
        QueryBuilder query = null;
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isBlank(esParamDto.getKeyword())) {
            query = boolQueryBuilder;
        } else {

            if (esParamDto.getQueryBuilder() != null) {
                query = (QueryBuilder) esParamDto.getQueryBuilder();
            } else {

                String matchField = esParamDto.getMatchField();
                if (StringUtils.isBlank(matchField)) {
                    throw new RuntimeException("matchField is error ");
                }
                String[] split = matchField.split(",");
                for (String key : split) {
                    QueryBuilder queryBuilder = QueryBuilders.matchQuery(key, esParamDto.getKeyword())
                            .analyzer(CommonConstant.analysis
                            );
                    //创建 QueryBuilder
                    boolQueryBuilder = boolQueryBuilder.should(queryBuilder);

                }
                query = boolQueryBuilder; //最后 这个是查询
            }
        }
        //分页并且高亮
        if (esParamDto.isPage() && esParamDto.isHightLight()) {
            return ElasticsearchUtil.searchDataPage(esParamDto.getIndex(), esParamDto.getType(), esParamDto.getPageNum()
                    , esParamDto.getPageSize()
                    , query, esParamDto.getFields(), esParamDto.getSortField(), esParamDto.getHightLightField());
        }
        //只分页无需高亮
        else if (esParamDto.isPage() && !esParamDto.isHightLight()) {
            return ElasticsearchUtil.searchDataPage(esParamDto.getIndex(), esParamDto.getType(), esParamDto.getPageNum()
                    , esParamDto.getPageSize()
                    , query, esParamDto.getFields(), esParamDto.getSortField(), null);
        }
        // 只高亮无需分页
        else if (!esParamDto.isPage() && esParamDto.isHightLight()) {
            return ElasticsearchUtil.searchListData(esParamDto.getIndex(), esParamDto.getType(), query, esParamDto.getDocumentSize(),
                    esParamDto.getFields(), esParamDto.getSortField(), esParamDto.getHightLightField());
        } else {// 不高亮 不分页
            return ElasticsearchUtil.searchListData(esParamDto.getIndex(), esParamDto.getType(), query, esParamDto.getDocumentSize(),
                    esParamDto.getFields(), esParamDto.getSortField(), null);
        }
    }

    @Override
    public Object searchById(ESParamDto esParamDto) {
        return ElasticsearchUtil.searchDataById(esParamDto.getIndex(), esParamDto.getType(), esParamDto.getEsId(), esParamDto.getMatchField());
    }


    @Override
    public boolean createIndex(String index) {
        return ElasticsearchUtil.createIndex(index);
    }

    @Override
    public boolean deleteIndex(String index) {
        return ElasticsearchUtil.deleteIndex(index);
    }

    @Override
    public boolean deleteIndex(Class indexClass) {
        return false;
    }

    @Override
    public String addData(JSONObject jsonObject, String index, String type) {
        return ElasticsearchUtil.addData(jsonObject, index, type);

    }

    @Override
    public String addDataId(JSONObject jsonObject, String index, String type, String id) {
        return ElasticsearchUtil.addData(jsonObject, index, type, id);
    }

    @Override
    public void deleteDataById(String index, String type, String id) {
        ElasticsearchUtil.deleteDataById(index, type, id);
    }

    @Override
    public void deleteDataByIdMany(String index, String type, List<String> ids) {
        for (String id : ids) {
            deleteDataById(index, type, id);
        }
    }

    @Override
    public void updateDataById(JSONObject jsonObject, String index, String type, String id) {
        ElasticsearchUtil.updateDataById(jsonObject, index, type, id);
    }

    @Override
    public Map<String, Object> searchDataById(String index, String type, String id, String fields) {
        return ElasticsearchUtil.searchDataById(index, type, id, fields);
    }

    @Override
    public Map<String, Object> searchDataByOneField(String index, String type, String fieldValue) {
        return ElasticsearchUtil.searchDataByOneField(index, type, fieldValue);
    }

    @Override
    public boolean doLikeByEsId(ArticleDto articleDto, String index, String type) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(MapperUtils.obj2jsonIgnoreNull(articleDto));
            ElasticsearchUtil.updateDataById(jsonObject,index, type, articleDto.getEsId());
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
