package com.kevin.cloud.provider.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "si_article")
public class SiArticle implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 名称
     */
    @Column(name = "mc")
    private String mc;

    /**
     * 围观人数
     */
    @Column(name = "wgrs")
    private Long wgrs;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 备注
     */
    @Column(name = "bz")
    private String bz;

    /**
     * 类型
     */
    @Column(name = "`type`")
    private String type;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 评论编号
     */
    @Column(name = "pl")
    private Long pl;

    /**
     * 关键字
     */
    @Column(name = "keywords")
    private String keywords;

    /**
     * 描述
     */
    @Column(name = "`describe`")
    private String describe;

    /**
     * 栏目
     */
    @Column(name = "category")
    private Long category;

    /**
     * 标签
     */
    @Column(name = "tags")
    private String tags;

    /**
     * 标题图片
     */
    @Column(name = "titlepic")
    private String titlepic;

    /**
     * 是否公开(默认公开)
     */
    @Column(name = "visibility")
    private String visibility;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private Long updateBy;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 发布状态
     */
    @Column(name = "fbzt")
    private String fbzt;

    /**
     * 是否代码发布
     */
    @Column(name = "sfdm")
    private String sfdm;

    private static final long serialVersionUID = 1L;
}