package com.kevin.cloud.commons.dto.blog.vo;

import com.kevin.cloud.commons.dto.QueryPageParam;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.commons.dto.blog.vo
 * @ClassName: CommentVo
 * @Author: kevin
 * @Description:
 * @Date: 2020/2/6 17:05
 * @Version: 1.0
 */
@Data
@ToString
public class CommentVo extends QueryPageParam implements Serializable  {
    private static final long serialVersionUID = 4346706789826435315L;
    /**
     * id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long wzid;

    /**
     * 评论时间
     */
    private Date plsj;

    /**
     * 评论内容
     */
    private String plnr;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 评论人浏览器类型
     */
    private String browse;

    /**
     * 操作系统
     */
    private String osname;

    /**
     * 评论人地址
     */
    private String address;

    /**
     * 评论人（昵称）
     */
    private String replyName;

    /**
     * 评论人头像地址
     */
    private String img;

    /**
     * 评论人邮箱
     */
    private String email;

    /**
     * 父评论id
     */
    private Long pid;

    /**
     * 点赞人数
     */
    private Integer liks;

    /**
     * 被评论者昵称
     */
    private String toName;

    /**
     * 被评论者头像
     */
    private String toAvatar;

    /**
     * 是否是留言 默认不是留言为0
     */
    private Integer isLy;

}
