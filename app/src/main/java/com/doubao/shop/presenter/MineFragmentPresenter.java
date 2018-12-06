package com.doubao.shop.presenter;


import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.fragment.MineFragment;
import com.doubao.shop.model.MineFragmentModel;

/**
 * Created by zhuzhen
 */

public class MineFragmentPresenter extends BasePresenter<MineFragmentModel,MineFragment> {

    public void getUserAccount() {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getAccount(new MineFragmentModel.AccountInterFace() {
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
    public MineFragmentModel loadModel() {
        return new MineFragmentModel();
    }


}
