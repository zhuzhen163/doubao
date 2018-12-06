package com.doubao.shop.view;


import com.doubao.shop.base.BaseView;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface LoginActivityView extends BaseView {
    //登录
    void toLoginSuccess(String loginBean);
    void toLoginFail(String message);
    //获取短信验证码
    void getMessageSuccess(String message);
    void getMessageFail(String message);
}
