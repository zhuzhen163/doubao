package com.doubao.shop.presenter;


import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.fragment.HomeFragment;
import com.doubao.shop.model.HomeFragmentModel;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class HomeFragmentPresenter extends BasePresenter<HomeFragmentModel,HomeFragment> {

    @Override
    public HomeFragmentModel loadModel() {
        return new HomeFragmentModel();
    }
}
