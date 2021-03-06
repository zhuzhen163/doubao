package com.doubao.shop.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.tools.SwitchActivityManager;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/12/3
* description: 账户中心
*/
public class AccountCenterActivity extends BaseActivity {

    @BindView(R.id.ll_addressManager)
    LinearLayout ll_addressManager;
    @BindView(R.id.ll_accountSafe)
    LinearLayout ll_accountSafe;

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
        ll_accountSafe.setOnClickListener(this);
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
            case R.id.ll_accountSafe:
                SwitchActivityManager.startAccountSafeActivity(AccountCenterActivity.this);
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SwitchActivityManager.exitActivity(AccountCenterActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
