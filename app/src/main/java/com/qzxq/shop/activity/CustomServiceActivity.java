package com.qzxq.shop.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.tools.SwitchActivityManager;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/12/3
* description: 客户服务
*/
public class CustomServiceActivity extends BaseActivity {

    @BindView(R.id.ll_feedback)
    LinearLayout ll_feedback;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        setTitleName("客户服务");
    }

    @Override
    protected void initListener() {
        ll_feedback.setOnClickListener(this);
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(CustomServiceActivity.this);
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_service;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.ll_feedback:
                SwitchActivityManager.startFeedBackActivity(CustomServiceActivity.this);
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
