package com.shsxt.po;

public class UserModel {
    private Integer userId;
    private String userIdb64;

    public String getUserIdb64() {
        return userIdb64;
    }

    public void setUserIdb64(String userIdb64) {
        this.userIdb64 = userIdb64;
    }

    private String userName;
    private String trueName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
