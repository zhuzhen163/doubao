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
* description: 账户中心
*/
public class AccountCenterActivity extends BaseActivity {

    @BindView(R.id.ll_addressManager)
    LinearLayout ll_addressManager;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        setTitleName("账户中心");
    }

    @Override
    protected void initListener() {
        ll_addressManager.setOnClickListener(this);
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(AccountCenterActivity.this);
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_center;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.ll_addressManager:
                SwitchActivityManager.startAddressManagerActivity(AccountCenterActivity.this,"0");
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
