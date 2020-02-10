package com.kevin.cloud.commons.dto.cloud.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.cloud.dto
 * @ClassName: SmsDto
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/10 12:22
 * @Version: 1.0
 */
@Data
@ToString
public class SmsDto implements Serializable {

    private String BizId;
    private String Code;
    private String RequestId;
    private String Message;

    private String randomCode; //系统生成的验证码


}
