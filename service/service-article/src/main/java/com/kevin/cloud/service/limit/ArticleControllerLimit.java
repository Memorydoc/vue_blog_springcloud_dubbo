package com.kevin.cloud.service.limit;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.kevin.cloud.commons.platform.dto.ResponseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.service.limit
 * @ClassName: ArticleControllerLimit
 * @Author: kevin
 * @Description: 自定义sentinle 降级
 * @Date: 2020/2/4 13:10
 * @Version: 1.0
 */
public class ArticleControllerLimit {

    /**
     * 这里的参数  除了加一个 BlockException 其它的参数必须和 对应的Controller方法中的参数一模一样，不然会失效
     * @param esId
     * @param request
     * @param d
     * @return
     */
    public  ResponseResult doLikeLimit(String esId, HttpServletRequest request, BlockException d){
        System.out.println("进来了");
        return new ResponseResult(ResponseResult.CodeStatus.FAIL, "操作过于频繁", d);
    }
}
