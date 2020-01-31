package com.kevin.cloud.provider.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "si_flink")
public class SiFlink implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 链接名称
     */
    @Column(name = "ljmc")
    private String ljmc;

    /**
     * 链接地址
     */
    @Column(name = "ljdz")
    private String ljdz;

    /**
     * 图像地址
     */
    @Column(name = "titlepic")
    private String titlepic;

    /**
     * 描述
     */
    @Column(name = "`describe`")
    private String describe;

    /**
     * 状态
     */
    @Column(name = "zt")
    private String zt;

    /**
     * target
     */
    @Column(name = "target")
    private String target;

    /**
     * rel
     */
    @Column(name = "rel")
    private String rel;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    private static final long serialVersionUID = 1L;
}