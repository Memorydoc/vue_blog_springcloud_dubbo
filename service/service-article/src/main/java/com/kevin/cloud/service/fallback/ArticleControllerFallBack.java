package com.kevin.cloud.service.fallback;

import com.kevin.cloud.commons.platform.dto.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service.fallback
 * @ClassName: ArticleControllerFallBack
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/4 12:35
 * @Version: 1.0
 */
public class ArticleControllerFallBack {
    private static  final Logger logger = LoggerFactory.getLogger(ArticleControllerFallBack.class);

    public static ResponseResult doLikeFallBack(Throwable ex){
        return  new ResponseResult(ResponseResult.CodeStatus.OK, "点赞熔断", ex.toString());
    }
}
