package com.doubao.shop.entity;

/**
* @author zhuzhen
* create at 2018/11/23
* description:
*/
public class UserInfo {

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public class User{
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
        private String idcard;

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

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
