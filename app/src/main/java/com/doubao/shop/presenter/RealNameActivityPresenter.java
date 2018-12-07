package com.doubao.shop.presenter;


import com.doubao.shop.activity.RealNameActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.model.RealNameActivityModel;

/**
 * Created by zhuzhen
 */

public class RealNameActivityPresenter extends BasePresenter<RealNameActivityModel,RealNameActivity> {

    public void bindUser(String username,String idcard){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().bindUser(username,idcard, new RealNameActivityModel.InterFace() {
            @Override
            public void realNameSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().realNameSuccess(s);
                }
            }

            @Override
            public void realNameFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().realNameFail(s);
                }
            }
        });

    }

    @Override
    public RealNameActivityModel loadModel() {
        return new RealNameActivityModel();
    }
}
