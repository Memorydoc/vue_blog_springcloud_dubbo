package com.kevin.cloud.commons.dto;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 云平台基础dto模型
 * @author: kevin
 * @create: 2020-01-10 17:13
 **/
public class CloudBaseDto {
    private String fallbackReason;


    public String getFallbackReason() {
        return fallbackReason;
    }

    public void setFallbackReason(String fallbackReason) {
        this.fallbackReason = fallbackReason;
    }
}
