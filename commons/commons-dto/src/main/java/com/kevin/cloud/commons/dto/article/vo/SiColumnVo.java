package com.kevin.cloud.commons.dto.article.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kevin.cloud.commons.dto.QueryPageParam;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.article.vo
 * @ClassName: SiColumnVo
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/8 15:23
 * @Version: 1.0
 */
@Data
@ToString
public class SiColumnVo extends QueryPageParam implements Serializable {
    private static final long serialVersionUID = -6649267413778179761L;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    /**
     *要删除分类的数组
     */
    private List<String > deleteIdArr;


}
