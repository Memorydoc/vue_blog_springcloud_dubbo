package com.kevin.cloud.provider.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @program: vue-blog-backend
 * @description: 文章es 辅助类
 * @author: kevin
 * @create: 2020-01-21 13:55
 **/

@Data
@Document(indexName = "article", type  = "item", shards = 2)
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ArticleESDto implements BaseESDto, Serializable {

    @Autowired
    private ESManagement esManagement;

    @PostConstruct
    public void init() {
        esManagement.getProcessorMap().put("elasticsearchClientService",this);
    }

    private static final long serialVersionUID = 4870754588150348713L;
    /**
     * id
     */
    @Field(type = FieldType.Long)
    @Id
    private Long id;

    /**
     * 名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String mc;

    /**
     * 围观人数
     */
    @Field(type = FieldType.Long)
    private Long wgrs;

    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String content;

    /**
     * 备注
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String bz;

    /**
     * 类型
     */
    @Field(type = FieldType.Keyword)
    private String type;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    /**
     * 标题图片
     */
    @Field(type = FieldType.Keyword, index = false)
    private String titlepic;

    /**
     * 标题图片
     */
    @Field(type = FieldType.Keyword, index = false)
    private String titlepicname;

    /**
     * 标题图片
     */
    @Field(type = FieldType.Long, index = false)
    private String createDate;


    /**
     * 标题图片
     */
    @Field(type = FieldType.Long, index = false)
    private String updateDate;

    /**
     * 所属分类
     */
    @Field(type = FieldType.Text, index = false)
    private String categoryName;

    /**
     * 喜欢人数
     */
    @Field(type = FieldType.Integer)
    private Integer liks;

    /**
     * 是否特别推荐 默认不推荐为0
     */
    @Field(type = FieldType.Integer)
    private Integer tuijian;

}
