package com.kevin.cloud.provider.api;


import com.alibaba.fastjson.JSONObject;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.platform.dto.ESParamDto;

import java.util.List;
import java.util.Map;

/**
 * @program: vue-blog-backend
 * @description: es 顶级接口
 * @author: kevin
 * @create: 2020-01-21 13:51
 **/
public interface ESService {
    public Object search(ESParamDto esParamDto);
    public Object searchById(ESParamDto esParamDto);
    public boolean createIndex(String index); // 这里使用 spring注解的方式创建索引，所以一般用不到这个
    public boolean deleteIndex(String index);
    public boolean deleteIndex(Class indexClass);

    public String addData(JSONObject jsonObject, String index, String type) throws Exception; // 添加数据 无需指定id， UUID 自动生成ID
    public String addDataId(JSONObject jsonObject, String index, String type, String id) throws  Exception;// 指定ID添加数据

    public void deleteDataById(String index, String type, String id);// 通过id删除数据
    public void deleteDataByIdMany(String index, String type, List<String> ids);// 批量刪除


    public void updateDataById(JSONObject jsonObject, String index, String type, String id);// 通过id 更新数据
    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public Map<String, Object> searchDataById(String index, String type, String id, String fields);// 通过id查询数据


    boolean doLikeByEsId(ArticleDto articleDto, String index, String type);
}
