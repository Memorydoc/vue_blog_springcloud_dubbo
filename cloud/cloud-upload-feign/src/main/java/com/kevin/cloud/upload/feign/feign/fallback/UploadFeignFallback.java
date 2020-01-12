package com.kevin.cloud.upload.feign.feign.fallback;

import com.kevin.cloud.commons.utils.MapperUtils;
import com.kevin.cloud.upload.feign.feign.UploadFeign;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传熔断器
 * <p>
 * Description:
 * </p>
 *
 * @author kevin
 * @version v1.0.0
 */
@Component
public class UploadFeignFallback implements UploadFeign {

    private static final String BREAKING_MESSAGE = "请求失败了，请检查您的网络";

    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            return MapperUtils.obj2json(new ResponseResult<Void>(ResponseResult.CodeStatus.BREAKING, BREAKING_MESSAGE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
