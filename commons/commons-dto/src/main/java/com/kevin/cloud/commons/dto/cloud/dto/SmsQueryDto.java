package com.kevin.cloud.commons.dto.cloud.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.cloud.dto
 * @ClassName: SmsQueryDto
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/10 14:28
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@ToString
public class SmsQueryDto implements Serializable {
    private static final long serialVersionUID = -7427710978752604134L;
    private List<SmsSendDetailDTO> SmsSendDetailDTOs;
    private String Code;
    private String Message;
    private String RequestId;
    private String TotalCount;

    public SmsQueryDto() {
    }
}
