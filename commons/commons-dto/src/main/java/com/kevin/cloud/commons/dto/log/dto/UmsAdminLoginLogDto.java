package com.kevin.cloud.commons.dto.log.dto;

import java.util.Date;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-12 23:13
 **/
public class UmsAdminLoginLogDto {
    private Long id;
    private Long adminId;


    private Date createTime;

    private String ip;

    private String address;

    /**
     * 浏览器登录类型
     */
    private String userAgent;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return admin_id
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * @param adminId
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取浏览器登录类型
     *
     * @return user_agent - 浏览器登录类型
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置浏览器登录类型
     *
     * @param userAgent 浏览器登录类型
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
