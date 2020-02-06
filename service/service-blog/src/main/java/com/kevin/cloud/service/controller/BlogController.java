package com.kevin.cloud.service.controller;

import com.kevin.cloud.commons.dto.blog.dto.CommentDto;
import com.kevin.cloud.commons.dto.blog.dto.TypeViewDto;
import com.kevin.cloud.commons.dto.blog.vo.CommentVo;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.provider.api.BlogService;
import com.kevin.cloud.provider.api.SiFinkService;
import com.kevin.cloud.provider.domain.SiComment;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: vue-blog-backend
 * @description: 博客公共模块接口
 * @author: kevin
 * @create: 2020-01-15 11:25
 **/
@RequestMapping("blog")
@RestController
public class BlogController {


    @Reference(version = "1.0.0")
    private SiFinkService siFinkService;

    /**
     * 加载友情链接
     */
    @GetMapping("front/initLinks")
    public ResponseResult initLinks() {
        return new ResponseResult(ResponseResult.CodeStatus.OK, "", siFinkService.initLinks());
    }


    @Reference(version = "1.0.0")
    private BlogService blogService;

    /**
     * 查询分类界面数据
     */
    @GetMapping("front/initTypesData")
    public ResponseResult initTypesData() {
        List<TypeViewDto> typeViewDtos = blogService.initTypesData();
        return new ResponseResult(ResponseResult.CodeStatus.OK, "", typeViewDtos);
    }

    /**
     * 加载所有的评论
     *
     * @Param 是否留言 1 代表是 0 代表否
     */
    @GetMapping("front/initCommentData")
    public ResponseResult initCommentData(@RequestParam("isReply") Integer isReply) {
        List<CommentDto> commentDto = blogService.initCommentData(isReply);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "", commentDto);
    }

    /**
     * 提交评论
     * @param commentVo
     * @return
     */
    @PostMapping("front/commitComment")
    public ResponseResult commitComment(@RequestBody CommentVo commentVo) {
        boolean bool = blogService.commitComment(commentVo);

        if(bool){
            return  new ResponseResult(ResponseResult.CodeStatus.OK, "评论成功", null);
        }else{
            return  new ResponseResult(ResponseResult.CodeStatus.FAIL, "评论失败", null);
        }
    }

    @GetMapping("front/commentLiks")
    public ResponseResult commentLiks(@RequestParam("commentId") String commentId){
        int updateCount =  blogService.commentLiks(commentId);
        if(updateCount> 0){
            return  new ResponseResult(ResponseResult.CodeStatus.OK, "点赞成功", null);
        }
        return  new ResponseResult(ResponseResult.CodeStatus.FAIL, "点赞失败", null);
    }


}
