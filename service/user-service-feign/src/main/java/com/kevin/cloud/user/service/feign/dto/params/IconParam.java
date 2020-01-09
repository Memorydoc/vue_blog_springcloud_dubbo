package com.kevin.cloud.user.service.feign.dto.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改头像参数
 */
@Data
public class IconParam implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像地址
     */
    private String path;

}
