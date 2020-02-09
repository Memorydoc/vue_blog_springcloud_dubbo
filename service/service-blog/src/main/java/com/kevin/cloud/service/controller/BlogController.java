package com.kevin.cloud.service.controller;

import com.kevin.cloud.commons.dto.article.vo.SiColumnVo;
import com.kevin.cloud.commons.dto.blog.dto.CommentDto;
import com.kevin.cloud.commons.dto.blog.dto.TypeViewDto;
import com.kevin.cloud.commons.dto.blog.vo.CommentVo;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.provider.api.BlogService;
import com.kevin.cloud.provider.api.SiFinkService;
import com.kevin.cloud.provider.domain.SiColumnType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "加载文章评论数据")
    @GetMapping("front/loadCommentData")
    public ResponseResult loadCommentData(@ApiParam("esId")String esId){
        List<CommentDto> commentDto = blogService.loadCommentData(esId);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "文章评论数据加载成功", commentDto);
    }

    @ApiOperation(value = "文章评论提交")
    @PostMapping("front/articleCommentSubmit")
    public ResponseResult articleCommentSubmit(@RequestBody CommentVo commentVo){
        boolean bool = blogService.articleCommentSubmit(commentVo);
        if(bool){
            return  new ResponseResult(ResponseResult.CodeStatus.OK, "文章评论成功", null);
        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "文章评论成功", null);
        }
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

    @ApiOperation(value = "加载分类list")
    @PostMapping("initColumnTypesData")
    public ResponseResult initColumnTypesData(@RequestBody SiColumnVo siColumnVo){
        PageResult pageResult = blogService.initColumnTypesData(siColumnVo);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "分类列表查询成功", pageResult);
    }


    @ApiOperation(value =  "删除分类")
    @PostMapping("deleteColumn")
    public ResponseResult deleteColumn(@RequestBody SiColumnVo siColumnVo){
       int deleteCount =  blogService.deleteColumn(siColumnVo);
       if(deleteCount > 0){
           return new ResponseResult(ResponseResult.CodeStatus.OK, "分类删除成功", null);
       }else{
           return new ResponseResult(ResponseResult.CodeStatus.FAIL, "分类删除失败", null);
       }
    }

    @ApiOperation(value =  "添加分类")
    @PostMapping("addTypes")
    public ResponseResult addTypes(@RequestBody SiColumnVo siColumnVo){
        int insertCount = blogService.addTypes(siColumnVo);
        if(insertCount > 0){
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分类添加成功", null);
        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "分类添加失败", null);
        }
    }

    @ApiOperation(value = "修改分类")
    @PostMapping("editType")
    public ResponseResult editType(@RequestBody SiColumnVo siColumnVo){
        int updateCount = blogService.editType(siColumnVo);
        if(updateCount > 0){
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分类修改成功", null);
        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "分类修改失败", null);
        }
    }

    @ApiOperation("获取分类tag")
    @GetMapping("getTypeTags")
    public ResponseResult getTypeTags(){
        List<SiColumnType> siColumnTypeDtoList = blogService.getTypeTags();
        return  new ResponseResult(ResponseResult.CodeStatus.OK, "获取分类tag成功", siColumnTypeDtoList);
    }

}
