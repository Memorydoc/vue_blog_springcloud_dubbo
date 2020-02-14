package com.kevin.cloud.provider.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "si_column_type")
public class SiColumnType implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 分类名称
     */
    @Column(name = "flmc")
    private String flmc;

    /**
     * 栏目id
     */
    @Column(name = "lmid")
    private Long lmid;

    /**
     * 值
     */
    @Column(name = "`value`")
    private String value;

    /**
     * 备注
     */
    @Column(name = "bz")
    private String bz;

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
    private Date updateBy;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    private String delFlag;

    private static final long serialVersionUID = 1L;
}