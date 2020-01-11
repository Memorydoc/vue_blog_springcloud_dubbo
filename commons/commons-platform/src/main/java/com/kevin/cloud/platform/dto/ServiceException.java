package com.kevin.cloud.platform.dto;

/**
 * 全局业务异常
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 3034121940056795549L;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ServiceException() {

    }

    public ServiceException(ServiceStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
    }
}
