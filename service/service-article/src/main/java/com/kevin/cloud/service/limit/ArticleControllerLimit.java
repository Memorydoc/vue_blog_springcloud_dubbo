package com.kevin.cloud.service.limit;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service.limit
 * @ClassName: ArticleControllerLimit
 * @Author: kevin
 * @Description: 自定义sentinle 限流
 * @Date: 2020/2/4 13:10
 * @Version: 1.0
 */
public class ArticleControllerLimit {
   private static final Logger logger = LoggerFactory.getLogger(ArticleControllerLimit.class);
    /**
     * 这里的参数  除了加一个 BlockException 其它的参数必须和 对应的Controller方法中的参数一模一样，不然会失效
     *
     * 并且方法必须是static的，不然也无效， 不过如果不使用 blockHandlerClass 这种方式，直接声明blockHandler 则方法不是static的
     * @param esId
     * @param request
     * @param d
     * @return
     */
    public static ResponseResult doLikeLimit(String esId, HttpServletRequest request, BlockException d){
        logger.info("{}点赞限流触发", esId);
        return new ResponseResult(ResponseResult.CodeStatus.FAIL, "操作过于频繁", d);
    }
}
