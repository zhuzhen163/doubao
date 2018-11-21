package com.qzxq.shop.fragment;

import android.os.Bundle;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseFragment;
import com.qzxq.shop.presenter.HomeFragmentPresenter;
import com.qzxq.shop.view.HomeFragmentView;

/**
 * 主页
 * Created by zhuzhen on 2017/11/2.
 */

public class HomeFragment extends BaseFragment<HomeFragmentPresenter> implements HomeFragmentView {


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void release() {

    }

    @Override
    protected HomeFragmentPresenter loadMPresenter() {
        return new HomeFragmentPresenter();
    }

    @Override
    protected void initListener() {
    }


    @Override
    protected void initView() {
        initFragment();
    }

    private void initFragment(){
    }

    @Override
    public void showLoading() {
        setShowLoading(true);
    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

}
