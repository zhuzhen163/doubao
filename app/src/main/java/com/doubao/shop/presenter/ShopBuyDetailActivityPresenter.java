package com.doubao.shop.presenter;


import com.doubao.shop.activity.ShopBuyDetailActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.model.ShopBuyDetailActivityModel;

/**
 * Created by zhuzhen
 */

public class ShopBuyDetailActivityPresenter extends BasePresenter<ShopBuyDetailActivityModel,ShopBuyDetailActivity> {

    public void orderSubmit(String type,String addressId,String couponId) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().orderSubmit(type,addressId,couponId,new ShopBuyDetailActivityModel.SubmitInterFace() {
            @Override
            public void submitSuccess(String s) {
                if (getView() != null) {
                    getView().hideLoading();
                    getView().submitSuccess(s);
                }
            }

            @Override
            public void submitFail(String s) {
                if (getView() != null) {
                    getView().hideLoading();
                    getView().submitFail(s);
                }
            }
        });
    }

    public void checkCart(String type,String addressId,String couponId) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().checkCart(type,addressId,couponId,new ShopBuyDetailActivityModel.CheckCartInterFace() {
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