package com.kevin.cloud.commons.dto.cloud.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.cloud.dto
 * @ClassName: ss
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/10 17:52
 * @Version: 1.0
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SmsSendDetailDTO implements Serializable {
    private static final long serialVersionUID = 5179736757679578027L;
    private String phoneNum;
    private Long sendStatus;
    private String errCode;
    private String templateCode;
    private String content;
    private String sendDate;
    private String receiveDate;
    private String outId;
}