package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.article.vo.SiColumnVo;
import com.kevin.cloud.commons.dto.blog.dto.CommentDto;
import com.kevin.cloud.commons.dto.blog.dto.TypeViewDto;
import com.kevin.cloud.commons.dto.blog.vo.CommentVo;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.provider.domain.SiColumnType;

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

    List<CommentDto> loadCommentData(String esId);

    boolean articleCommentSubmit(CommentVo commentVo);

    PageResult initColumnTypesData(SiColumnVo siColumnVo);

    int deleteColumn(SiColumnVo siColumnVo);

    int addTypes(SiColumnVo siColumnVo);

    int editType(SiColumnVo siColumnVo);

    List<SiColumnType> getTypeTags();
}
