package com.qzxq.shop.presenter;


import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.fragment.HomeFragment;
import com.qzxq.shop.model.HomeFragmentModel;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class HomeFragmentPresenter extends BasePresenter<HomeFragmentModel,HomeFragment> {

    @Override
    public HomeFragmentModel loadModel() {
        return new HomeFragmentModel();
    }
}
