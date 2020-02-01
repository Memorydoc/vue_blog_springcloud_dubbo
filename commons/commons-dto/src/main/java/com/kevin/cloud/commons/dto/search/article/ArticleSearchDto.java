package com.kevin.cloud.commons.dto.search.article;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.search.article
 * @ClassName: ArticleSearchDto
 * @Author: kevin
 * @Description: es 使用
 * @Date: 2020/2/1 0:32
 * @Version: 1.0
 */
@Data
public class ArticleSearchDto implements Serializable {

    private static final long serialVersionUID = -4346910011534908296L;
    private List<Long> deleteIdArr;

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
     * 栏目Id
     */
    private Long category;


    /**
     * 栏目名
     */
    private String categoryName;


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
    private String  createDate;

    /**
     * 创建人
     */

    private String createBy;

    /**
     * 修改时间
     */
    private String updateDate;

    /**
     * 修改人
     */
    private String updateBy;

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
