package com.kevin.cloud.service.feign.dto.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码参数
 */
@Data
public class PasswordParam implements Serializable {

    private String username;
    private String oldPassword;
    private String newPassword;

}
