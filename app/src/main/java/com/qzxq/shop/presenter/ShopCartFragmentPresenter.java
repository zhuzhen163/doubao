package com.qzxq.shop.presenter;


import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.fragment.ShopCartFragment;
import com.qzxq.shop.model.ShopCartFragmentModel;

/**
 * Created by zhuzhen
 */

public class ShopCartFragmentPresenter extends BasePresenter<ShopCartFragmentModel,ShopCartFragment> {

    @Override
    public ShopCartFragmentModel loadModel() {
        return new ShopCartFragmentModel();
    }
}
