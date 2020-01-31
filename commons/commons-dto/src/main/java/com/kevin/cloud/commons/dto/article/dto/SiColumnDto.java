package com.kevin.cloud.commons.dto.article.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.article.dto
 * @ClassName: SiColumnDto
 * @Author: kevin
 * @Description:
 * @Date: 2020/1/31 13:07
 * @Version: 1.0
 */
@Data
public class SiColumnDto implements Serializable {
    private static final long serialVersionUID = -6877623115167719554L;

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


}
