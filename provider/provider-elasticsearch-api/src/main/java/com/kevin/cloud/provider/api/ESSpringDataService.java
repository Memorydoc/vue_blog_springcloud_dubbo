package com.kevin.cloud.provider.api;

import com.alibaba.fastjson.JSONObject;
import com.kevin.cloud.commons.platform.dto.ESParamDto;

import java.util.Map;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-22 22:14
 **/
public interface ESSpringDataService {
    public Object search(ESParamDto esParamDto);

    public boolean createIndex(String index); // 这里使用 spring注解的方式创建索引，所以一般用不到这个
    public boolean deleteIndex(Class indexClass);
    public boolean deleteIndex(String indexClass);
    public Object addData(String jsonObject) throws Exception; // 添加数据 无需指定id， UUID 自动生成ID

    public void deleteDataById(long id);// 通过id删除数据
    public void deleteData(Class cls) throws ClassNotFoundException, IllegalAccessException, InstantiationException, Exception;// 通过id删除数据
    public void updateDataById(String json) throws Exception;// 通过id 更新数据
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
}
