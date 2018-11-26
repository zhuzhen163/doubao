package com.qzxq.shop.activity;

import android.view.View;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.base.BasePresenter;

/**
* @author zhuzhen
* create at 2018/11/26
* description: 商品购买详情
*/
public class ShopBuyDetailActivity extends BaseActivity {
    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_buy_detail;
    }

    @Override
    protected void otherViewClick(View view) {

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
