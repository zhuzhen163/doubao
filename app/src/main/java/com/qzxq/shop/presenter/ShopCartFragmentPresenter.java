package com.qzxq.shop.presenter;


import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.fragment.ShopCartFragment;
import com.qzxq.shop.model.ShopCartFragmentModel;

/**
 * Created by zhuzhen
 */

public class ShopCartFragmentPresenter extends BasePresenter<ShopCartFragmentModel,ShopCartFragment> {

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
