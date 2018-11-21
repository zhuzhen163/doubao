package com.qzxq.shop.fragment;

import android.os.Bundle;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseFragment;
import com.qzxq.shop.presenter.ClassifyFragmentPresenter;
import com.qzxq.shop.view.ClassifyFragmentView;

/**
 * 分类
 * @author zhuzhen
 */
public class ClassifyFragment extends BaseFragment<ClassifyFragmentPresenter> implements ClassifyFragmentView {

    private static final String TAG = "ClassifyFragment";


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void release() {

    }

    @Override
    protected ClassifyFragmentPresenter loadMPresenter() {
        return new ClassifyFragmentPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        initRecycleView();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    private void initRecycleView() {
    }

}
