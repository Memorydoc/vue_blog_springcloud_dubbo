package com.kevin.cloud.commons.dto.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kevin.cloud.commons.dto.QueryPageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-17 16:23
 **/

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmsAdminVo extends QueryPageParam {
    private List<Long> deleteUserlist;

    private Long id;

    private String username;

    private String password;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 备注信息
     */
    private String note;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    private Integer status;

    private Integer isCustomer;
    /**
     * 手机登录使用
     */
    private Long phone; // 手机号
    private String bizId; // 生成验证码的业务id
    private String randomCode;// 验证码
}
