package com.doubao.shop.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.tools.SwitchActivityManager;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/12/3
* description: 客户服务
*/
public class CustomServiceActivity extends BaseActivity {

    @BindView(R.id.ll_feedback)
    LinearLayout ll_feedback;
    @BindView(R.id.ll_helpCenter)
    LinearLayout ll_helpCenter;

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
        ll_helpCenter.setOnClickListener(this);
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
            case R.id.ll_helpCenter:
                SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"pages/ucenter/helpCenter","帮助中心");
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
