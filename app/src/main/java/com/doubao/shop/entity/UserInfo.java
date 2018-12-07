package com.doubao.shop.entity;

/**
* @author zhuzhen
* create at 2018/11/23
* description:
*/
public class UserInfo {
    private String userId;
    private String username;
    private String password;
    private String gender;
    private String birthday;
    private String register_time;
    private String last_login_time;
    private String last_login_ip;
    private String user_level_id;
    private String nickname;
    private String mobile;
    private String register_ip;
    private String avatar;
    private String weixin_openid;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
