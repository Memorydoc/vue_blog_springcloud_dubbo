package com.kevin.cloud.commons.platform.dto;


import com.kevin.cloud.commons.utils.MapperUtils;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 云平台 feign 基础模型
 * @author: kevin
 * @create: 2020-01-10 17:15
 **/
public class CloudBaseFeign {
    private static final String BREAKING_MESSAGE = "请求失败了，请检查您的网络";

    public String error() {
        CloudBaseDto cloudBaseDto = new CloudBaseDto();
        cloudBaseDto.setFallbackReason(BREAKING_MESSAGE);
        try {
            return MapperUtils.obj2json(new ResponseResult<CloudBaseDto>(ResponseResult.CodeStatus.BREAKING, BREAKING_MESSAGE, cloudBaseDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}
