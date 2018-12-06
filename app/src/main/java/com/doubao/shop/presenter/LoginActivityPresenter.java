package com.doubao.shop.presenter;


import com.doubao.shop.activity.LoginActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.model.LoginActivityModel;

/**
 * Created by zhuzhen
 */

public class LoginActivityPresenter extends BasePresenter<LoginActivityModel,LoginActivity> {

    public void getLoginPresenter(String mobile,String code){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().toLogin(mobile,code, new LoginActivityModel.LoginInterFace() {
            @Override
            public void toLoginSuccess(String loginBean) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().toLoginSuccess(loginBean);
                }
            }

            @Override
            public void toLoginFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().toLoginFail(s);
                }
            }
        });

    }

    public void getSmsCodePresenter(String userMobile){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getSmsCode(userMobile, new LoginActivityModel.MessageCodeInterFace() {
            @Override
            public void getSuccessSms(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().getMessageSuccess(s);
                }

            }

            @Override
            public void getFailSms(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().getMessageFail(s);
                }
            }
        });

    }

    @Override
    public LoginActivityModel loadModel() {
        return new LoginActivityModel();
    }
}
