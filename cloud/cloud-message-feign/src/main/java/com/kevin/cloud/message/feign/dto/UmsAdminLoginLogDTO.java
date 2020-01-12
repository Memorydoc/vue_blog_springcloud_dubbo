package com.kevin.cloud.message.feign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kevin.cloud.commons.platform.dto.CloudBaseDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-10 17:03
 **/
@Data
public class UmsAdminLoginLogDTO extends CloudBaseDto implements Serializable {
    private static final long serialVersionUID = -6610556441914830628L;
    private Long id;
    private Long adminId;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String ip;
    private String address;
    private String userAgent;
}
