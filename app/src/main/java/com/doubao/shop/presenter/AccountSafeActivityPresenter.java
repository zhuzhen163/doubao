package com.doubao.shop.presenter;


import com.doubao.shop.activity.AccountSafeActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.model.AccountSafeActivityModel;

/**
 * Created by zhuzhen
 */

public class AccountSafeActivityPresenter extends BasePresenter<AccountSafeActivityModel,AccountSafeActivity> {

    public void userInfo() {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().userInfo(new AccountSafeActivityModel.AccountInterFace() {
            @Override
            public void getAccountSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().getAccountSuccess(s);
                }
            }

            @Override
            public void getAccountFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().getAccountFail(s);
                }
            }
        });
    }
    @Override
    public AccountSafeActivityModel loadModel() {
        return new AccountSafeActivityModel();
    }

}
