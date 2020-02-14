package com.kevin.cloud.provider.mapper;

import com.kevin.cloud.commons.dto.blog.dto.CommentDto;
import com.kevin.cloud.provider.domain.SiComment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SiCommentMapper extends Mapper<SiComment> {
    List<CommentDto> initCommentParentData(@Param("isReply") Integer isReply);
    List<CommentDto> initCommentChildrenData(@Param("isReply") Integer isReply);
}