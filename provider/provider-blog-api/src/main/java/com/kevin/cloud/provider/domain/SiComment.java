package com.kevin.cloud.provider.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "si_comment")
public class SiComment implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 文章id
     */
    @Column(name = "wzid")
    private Long wzid;

    /**
     * 评论时间
     */
    @Column(name = "plsj")
    private Date plsj;

    /**
     * 评论内容
     */
    @Column(name = "plnr")
    private String plnr;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private Long updateBy;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 评论人浏览器类型
     */
    @Column(name = "`browse`")
    private String browse;

    /**
     * 操作系统
     */
    @Column(name = "osname")
    private String osname;

    /**
     * 评论人地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 评论人（昵称）
     */
    @Column(name = "replyName")
    private String replyName;

    /**
     * 评论人头像地址
     */
    @Column(name = "img")
    private String img;

    /**
     * 评论人邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 父评论id
     */
    @Column(name = "pid")
    private Long pid;

    /**
     * 点赞人数
     */
    @Column(name = "liks")
    private Integer liks;

    /**
     * 被评论者昵称
     */
    @Column(name = "to_name")
    private String toName;

    /**
     * 被评论者头像
     */
    @Column(name = "to_avatar")
    private String toAvatar;

    /**
     * 是否是留言 默认不是留言为0
     */
    @Column(name = "is_ly")
    private Integer isLy;

    public SiComment() {
    }

    private static final long serialVersionUID = -7386746852077320299L;
}