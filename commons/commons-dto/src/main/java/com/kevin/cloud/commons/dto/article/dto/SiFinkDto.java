package com.kevin.cloud.commons.dto.article.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.article.dto
 * @ClassName: SiFinkDto
 * @Author: kevin
 * @Description:
 * @Date: 2020/1/31 23:09
 * @Version: 1.0
 */
@Data
public class SiFinkDto {
    private Long id;

    /**
     * 链接名称
     */
    private String ljmc;

    /**
     * 链接地址
     */
    private String ljdz;

    /**
     * 图像地址
     */
    private String titlepic;

    private String describe;

    private String zt;

    private String target;

    private String rel;

    private String delFlag;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateDate;
}
