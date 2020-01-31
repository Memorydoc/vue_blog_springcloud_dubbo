package com.kevin.cloud.provider.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "si_column")
public class SiColumn implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 栏目名称
     */
    @Column(name = "lmmc")
    private String lmmc;

    /**
     * 备注
     */
    @Column(name = "bz")
    private String bz;

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
     * 别名
     */
    @Column(name = "bm")
    private String bm;

    /**
     * 关键字
     */
    @Column(name = "gjz")
    private String gjz;

    /**
     * 栏目类型
     */
    @Column(name = "type_id")
    private Long typeId;

    /**
     * 描述
     */
    @Column(name = "`describe`")
    private String describe;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取栏目名称
     *
     * @return lmmc - 栏目名称
     */
    public String getLmmc() {
        return lmmc;
    }

    /**
     * 设置栏目名称
     *
     * @param lmmc 栏目名称
     */
    public void setLmmc(String lmmc) {
        this.lmmc = lmmc;
    }

    /**
     * 获取备注
     *
     * @return bz - 备注
     */
    public String getBz() {
        return bz;
    }

    /**
     * 设置备注
     *
     * @param bz 备注
     */
    public void setBz(String bz) {
        this.bz = bz;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取修改人
     *
     * @return update_by - 修改人
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置修改人
     *
     * @param updateBy 修改人
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取删除标志
     *
     * @return del_flag - 删除标志
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标志
     *
     * @param delFlag 删除标志
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取别名
     *
     * @return bm - 别名
     */
    public String getBm() {
        return bm;
    }

    /**
     * 设置别名
     *
     * @param bm 别名
     */
    public void setBm(String bm) {
        this.bm = bm;
    }

    /**
     * 获取关键字
     *
     * @return gjz - 关键字
     */
    public String getGjz() {
        return gjz;
    }

    /**
     * 设置关键字
     *
     * @param gjz 关键字
     */
    public void setGjz(String gjz) {
        this.gjz = gjz;
    }

    /**
     * 获取栏目类型
     *
     * @return type_id - 栏目类型
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * 设置栏目类型
     *
     * @param typeId 栏目类型
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取描述
     *
     * @return describe - 描述
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * 设置描述
     *
     * @param describe 描述
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", lmmc=").append(lmmc);
        sb.append(", bz=").append(bz);
        sb.append(", createDate=").append(createDate);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", bm=").append(bm);
        sb.append(", gjz=").append(gjz);
        sb.append(", typeId=").append(typeId);
        sb.append(", describe=").append(describe);
        sb.append("]");
        return sb.toString();
    }
}