package com.qzxq.shop.presenter;


import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.fragment.ShopCartFragment;
import com.qzxq.shop.model.ShopCartFragmentModel;

import okhttp3.RequestBody;

/**
 * Created by zhuzhen
 */

public class ShopCartFragmentPresenter extends BasePresenter<ShopCartFragmentModel,ShopCartFragment> {

    public void isCheck(RequestBody body){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().isCheck(body,new ShopCartFragmentModel.IsCheckCallBack() {
            @Override
            public void isCheckSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().isCheckSuccess(s);
                }
            }

            @Override
            public void isCheckFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().isCheckFail(s);
                }
            }
        });
    }


    public void cartDelete(RequestBody body){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().cartDelete(body,new ShopCartFragmentModel.DeleteCallBack() {
            @Override
            public void deleteSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().deleteSuccess(s);
                }
            }

            @Override
            public void deleteFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().deleteFail(s);
                }
            }
        });
    }

    public void getShopCartList(){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getShopCartList(new ShopCartFragmentModel.ShopCartList() {
            @Override
            public void getListSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().getListSuccess(s);
                }
            }

            @Override
            public void getListFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().getListFail(s);
                }
            }
        });
    }
    @Override
    public ShopCartFragmentModel loadModel() {
        return new ShopCartFragmentModel();
    }
}
