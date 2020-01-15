package com.kevin.cloud.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-15 12:08
 **/
@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
@Builder
public class RoleUserDto implements Serializable  {

    private static final long serialVersionUID = 5894694323761973930L;

    private Long uId;
    private Long id;
    private String roleCode;
    private String roleName;

    private Boolean isActive;

    private Long updateBy;

    private Long createBy;
    private Date createTime;
    private Date updateTime;
}
