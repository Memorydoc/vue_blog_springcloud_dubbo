package com.kevin.cloud.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-11 14:10
 **/
@Data
public class MessageCommonDto {
    private static final long serialVersionUID = -6610556441914830628L;
    private Long id;
    private Long adminId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String ip;
    private String address;
    private String userAgent;
}
