package com.kevin.cloud.provider.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
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
@Table(name = "organization")
public class Organization implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 机构名称
     */
    @Column(name = "organization_name")
    private String organizationName;

    /**
     * 是否激活 0 未激活 1 激活
     */
    @Column(name = "is_active")
    private Boolean isActive;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private Long updateBy;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}