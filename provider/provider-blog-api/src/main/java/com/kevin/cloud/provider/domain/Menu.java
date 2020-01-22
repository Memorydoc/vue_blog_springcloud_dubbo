package com.kevin.cloud.provider.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kevin.cloud.commons.platform.serializer.CustomJsonDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu")
public class Menu implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 菜单名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 连接地址
     */
    @Column(name = "navigation_url")
    private String navigationUrl;

    /**
     * 图标类名
     */
    @Column(name = "icon_class_name")
    private String iconClassName;

    /**
     * 父节点id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 顺序索引
     */
    @Column(name = "order_index")
    private Integer orderIndex;

    /**
     * 是否激活 0 未激活 1 激活
     */
    @Column(name = "is_active")
    private Boolean isActive;

    /**
     * 是否可见 0 不可见 1可见
     */
    @Column(name = "is_visible")
    private Boolean isVisible;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private Long updateBy;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static MenuBuilder builder() {
        return new MenuBuilder();
    }
}