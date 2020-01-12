package com.kevin.cloud.commons.platform.dto;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description: 在熔断降级方法统一返回对象
 * @author: kevin
 * @create: 2020-01-11 20:37
 **/
public class FallBackResult {

    private String fallbackReason;
    private Object data;

    private boolean status = true;// 熔断状态 默认为成功状态


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getFallbackReason() {
        return fallbackReason;
    }

    public void setFallbackReason(String fallbackReason) {
        this.fallbackReason = fallbackReason;
    }

    @Override
    public String toString() {
        return "FallBackResult{" +
                "fallbackReason='" + fallbackReason + '\'' +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
