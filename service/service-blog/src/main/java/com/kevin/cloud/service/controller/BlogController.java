package com.kevin.cloud.service.controller;

import com.kevin.cloud.commons.dto.article.vo.SiColumnVo;
import com.kevin.cloud.commons.dto.blog.dto.CommentDto;
import com.kevin.cloud.commons.dto.blog.dto.TypeViewDto;
import com.kevin.cloud.commons.dto.blog.vo.CommentVo;
import com.kevin.cloud.commons.dto.cloud.dto.SmsDto;
import com.kevin.cloud.commons.dto.cloud.dto.SmsSendDetailDTO;
import com.kevin.cloud.commons.dto.cloud.vo.SmsVo;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.provider.api.BlogService;
import com.kevin.cloud.provider.api.RedisTemplateService;
import com.kevin.cloud.provider.api.SiFinkService;
import com.kevin.cloud.provider.api.UserService;
import com.kevin.cloud.provider.domain.SiColumnType;
import com.kevin.cloud.service.feign.UserServiceFeign;
import com.kevin.cloud.sms.api.SmsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vue-blog-backend
 * @description: 博客公共模块接口
 * @author: kevin
 * @create: 2020-01-15 11:25
 **/
@RequestMapping("blog")
@RestController
public class BlogController {

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);


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
    public ResponseResult loadCommentData(@ApiParam("esId") String esId) {
        List<CommentDto> commentDto = blogService.loadCommentData(esId);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "文章评论数据加载成功", commentDto);
    }

    @ApiOperation(value = "文章评论提交")
    @PostMapping("front/articleCommentSubmit")
    public ResponseResult articleCommentSubmit(@RequestBody CommentVo commentVo) {
        boolean bool = blogService.articleCommentSubmit(commentVo);
        if (bool) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "文章评论成功", null);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "文章评论成功", null);
        }
    }


    /**
     * 提交评论
     *
     * @param commentVo
     * @return
     */
    @PostMapping("front/commitComment")
    public ResponseResult commitComment(@RequestBody CommentVo commentVo) {
        boolean bool = blogService.commitComment(commentVo);

        if (bool) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "评论成功", null);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "评论失败", null);
        }
    }

    @GetMapping("front/commentLiks")
    public ResponseResult commentLiks(@RequestParam("commentId") String commentId) {
        int updateCount = blogService.commentLiks(commentId);
        if (updateCount > 0) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "点赞成功", null);
        }
        return new ResponseResult(ResponseResult.CodeStatus.FAIL, "点赞失败", null);
    }

    @ApiOperation(value = "加载分类list")
    @PostMapping("initColumnTypesData")
    public ResponseResult initColumnTypesData(@RequestBody SiColumnVo siColumnVo) {
        PageResult pageResult = blogService.initColumnTypesData(siColumnVo);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "分类列表查询成功", pageResult);
    }


    @ApiOperation(value = "删除分类")
    @PostMapping("deleteColumn")
    public ResponseResult deleteColumn(@RequestBody SiColumnVo siColumnVo) {
        int deleteCount = blogService.deleteColumn(siColumnVo);
        if (deleteCount > 0) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分类删除成功", null);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "分类删除失败", null);
        }
    }




    @ApiOperation(value = "添加分类")
    @PostMapping("addTypes")
    public ResponseResult addTypes(@RequestBody SiColumnVo siColumnVo) {
        siColumnVo.setCreateBy(userServiceFeign.getCurrentUser().getId());
        int insertCount = blogService.addTypes(siColumnVo);
        if (insertCount > 0) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分类添加成功", null);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "分类添加失败", null);
        }
    }

    @ApiOperation(value = "修改分类")
    @PostMapping("editType")
    public ResponseResult editType(@RequestBody SiColumnVo siColumnVo) {
        int updateCount = blogService.editType(siColumnVo);
        if (updateCount > 0) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "分类修改成功", null);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "分类修改失败", null);
        }
    }

    @ApiOperation("获取分类tag")
    @GetMapping("getTypeTags")
    public ResponseResult getTypeTags() {
        List<SiColumnType> siColumnTypeDtoList = blogService.getTypeTags();
        return new ResponseResult(ResponseResult.CodeStatus.OK, "获取分类tag成功", siColumnTypeDtoList);
    }


    @Reference(version = "1.0.0")
    private SmsService smsService;


    @Reference(version = "1.0.0")
    private RedisTemplateService redisTemplateService;

    @Reference(version = "1.0.0")
    private UserService userService;


    @Resource
    private UserServiceFeign userServiceFeign;

    @ApiOperation(value = "获取验证码")
    @GetMapping("front/getSmsCode")
    public ResponseResult getSmsCode(@ApiParam("phone") String phone, @ApiParam("isRegister") String isRegister) {
        SmsVo smsVo = new SmsVo();
        smsVo.setPhoneNumber(phone);
        // 发短信之前，先判断当前手机号码是否已经注册
        if (StringUtils.isNotBlank(isRegister)) {
            boolean isExist = userService.judgePhoneUserIsExist(phone);
            if (isExist) {
                return new ResponseResult(ResponseResult.CodeStatus.FAIL, "当前用户已经存在", null);
            }
        }
        SmsDto smsDto = smsService.sendSmsDefault(smsVo);
        logger.info("短信发送结果" + String.valueOf(smsDto));
        if (smsDto.getBizId() == null) {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, smsDto.getMessage() == null ? "发送失败" : smsDto.getMessage(), null);
        }
        // 设置有效时间为60秒
        FallBackResult fallBackResult = redisTemplateService.set(smsDto.getBizId(), smsDto.getRandomCode(), 60);

        if ("OK".equalsIgnoreCase(smsDto.getCode()) && fallBackResult.isStatus()) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "发送成功", smsDto);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, smsDto.getMessage() == null ? "发送失败" : smsDto.getMessage(), null);
        }
    }


    @ApiOperation(value = "检测验证码", notes = "这是去阿里云检测验证码是否正确")
    @GetMapping("front/checkSmsCode")
    public ResponseResult checkSmsCode(@ApiParam("code") String code, @ApiParam("bizId") String bizId, @ApiParam("phone") String phone) {
        Map resultMap = new HashMap();
        SmsVo smsVo = new SmsVo();
        smsVo.setPhoneNumber(phone);
        smsVo.setBizId(bizId);
        SmsSendDetailDTO smsQueryDto = null;
        try {
            smsQueryDto = smsService.querySendDetails(smsVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo 这里要判断
        resultMap.put("result", "false");
        if (smsQueryDto != null) {
            if (StringUtils.isNotBlank(smsQueryDto.getContent())) {
                String[] arr = smsQueryDto.getContent().split(" ");
                if ((arr[1]).equalsIgnoreCase(code + ",")) {
                    resultMap.put("result", "true");
                    resultMap.put("data", smsQueryDto);
                    return new ResponseResult(ResponseResult.CodeStatus.OK, "验证成功", resultMap);
                }
            }
            return new ResponseResult(ResponseResult.CodeStatus.OK, "验证失败", resultMap);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "验证失败", resultMap);
        }
    }

    @ApiOperation(value = "从缓存检测验证码", notes = "这是从redis中检测验证码是否正确")
    @GetMapping("front/checkSmsCodeByRedis")
    public ResponseResult checkSmsCodeByRedis(@ApiParam("code") String code, @ApiParam("bizId") String bizId) {
        Object o = null;
        Map resultMap = new HashMap();
        resultMap.put("result", "false");
        try {
            o = redisTemplateService.get(bizId, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (o == null) {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "验证码失效", null);
        }
        if (code.equalsIgnoreCase(o.toString())) {
            resultMap.put("result", "true");
            // 验证码验证成功 修改注册用户验证状态
            redisTemplateService.set(bizId, "true");
            return new ResponseResult(ResponseResult.CodeStatus.OK, "验证成功", resultMap);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "验证成功", resultMap);
        }
    }

}
