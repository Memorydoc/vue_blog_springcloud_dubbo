package com.kevin.cloud.commons.dto.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kevin.cloud.commons.dto.serializer.CustomJsonDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-15 14:16
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto implements Serializable {

    private List<Long> deleteIdArr;

    private static final long serialVersionUID = -5031493477217878426L;
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String mc;

    /**
     * 围观人数
     */
    private Long wgrs;

    /**
     * 内容
     */
    private String content;

    /**
     * 备注
     */
    private String bz;

    /**
     * 类型
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 评论编号
     */
    private Long pl;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 描述
     */
    private String describe;

    /**
     * 栏目
     */
    private Long category;

    /**
     * 标签
     */
    private String tags;

    /**
     * 标题图片
     */
    private String titlepic;

    /**
     * 是否公开(默认公开)
     */
    private String visibility;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */

    private Long createBy;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 发布状态
     */
    private String fbzt;

    /**
     * 是否代码发布
     */
    private String sfdm;

    private String esId;
}
