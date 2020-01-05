package com.kevin.cloud.kevin.cloud.oauth2.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录参数
 */
@Data
public class LoginParam implements Serializable {

    private String username;
    private String password;

}
