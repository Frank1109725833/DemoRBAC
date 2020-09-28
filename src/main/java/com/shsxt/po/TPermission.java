package com.shsxt.po;

import java.io.Serializable;
import java.util.Date;

/**
 * (TPermission)实体类
 *
 * @author makejava
 * @since 2020-09-27 17:32:31
 */
public class TPermission implements Serializable {
    private static final long serialVersionUID = -95546403937640742L;

    private Integer id;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 模块ID
     */
    private Integer moduleId;
    /**
     * 权限值
     */
    private String aclValue;

    private Date createDate;

    private Date updateDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getAclValue() {
        return aclValue;
    }

    public void setAclValue(String aclValue) {
        this.aclValue = aclValue;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "TPermission{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", moduleId=" + moduleId +
                ", aclValue='" + aclValue + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}