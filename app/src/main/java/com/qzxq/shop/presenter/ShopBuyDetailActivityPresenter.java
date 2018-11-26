package com.qzxq.shop.presenter;


import com.qzxq.shop.activity.ShopBuyDetailActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.model.ShopBuyDetailActivityModel;

/**
 * Created by zhuzhen
 */

public class ShopBuyDetailActivityPresenter extends BasePresenter<ShopBuyDetailActivityModel,ShopBuyDetailActivity> {


    public void checkCart() {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().checkCart(new ShopBuyDetailActivityModel.CheckCartInterFace() {
            @Override
            public void checkSuccess(String s) {
                if (getView() != null) {
                    getView().hideLoading();
                    getView().checkSuccess(s);
                }
            }

            @Override
            public void checkFail(String s) {
                if (getView() != null) {
                    getView().hideLoading();
                    getView().checkFail(s);
                }
            }
        });
    }

    @Override
    public ShopBuyDetailActivityModel loadModel() {
        return new ShopBuyDetailActivityModel();
    }
}