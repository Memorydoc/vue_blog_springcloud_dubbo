package com.kevin.cloud.commons.dto.blog.dto;

import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.blog.dto
 * @ClassName: TypeViewDto
 * @Author: kevin
 * @Description: 博客分类界面数据展示DTO
 * @Date: 2020/2/3 16:52
 * @Version: 1.0
 */

@Data
public class TypeViewDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 栏目名称
     */
    private String lmmc;

    /**
     * 备注
     */
    private String bz;

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
     * 别名
     */
    private String bm;

    /**
     * 关键字
     */
    private String gjz;

    /**
     * 栏目类型
     */
    private Long typeId;

    /**
     * 描述
     */
    private String describe;


    private List<ArticleDto> articleDtoList;

}
