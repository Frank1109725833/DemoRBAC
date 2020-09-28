package com.shsxt.po;

import java.io.Serializable;
import java.util.Date;

/**
 * (TUserRole)实体类
 *
 * @author makejava
 * @since 2020-09-25 19:20:52
 */
public class TUserRole implements Serializable {
    private static final long serialVersionUID = 438952911794463439L;

    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createDate;

    private Date updateDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

}