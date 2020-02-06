package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.blog.dto.CommentDto;
import com.kevin.cloud.commons.dto.blog.dto.TypeViewDto;
import com.kevin.cloud.commons.dto.blog.vo.CommentVo;

import java.util.List;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.api
 * @ClassName: BlogService
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/3 16:50
 * @Version: 1.0
 */
public interface BlogService {
    List<TypeViewDto> initTypesData();

    List<CommentDto> initCommentData(Integer isReply);

    boolean commitComment(CommentVo commentVo);

    int commentLiks(String commentId);
}
