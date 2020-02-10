package com.kevin.cloud.commons.dto.cloud.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.cloud.vo
 * @ClassName: SmsVo
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/10 11:49
 * @Version: 1.0
 */
@Data
@ToString
public class SmsVo implements Serializable {
    private static final long serialVersionUID = -887814907856162580L;
    private String phoneNumber;

    /**
     * 查询使用
     */
    public String BizId;

    public Long CurrentPage;
    private Long PageSize;
    private String SendDate;

}
