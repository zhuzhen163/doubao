package com.qzxq.shop.activity;

import android.view.View;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.adapter.AddressManagerAdapter;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.presenter.AddressManagerActivityPresenter;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.widget.xrecyclerview.XRecyclerView;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/11/20
* description:地址管理
*/
public class AddressManagerActivity extends BaseActivity<AddressManagerActivityPresenter> implements View.OnClickListener{

    @BindView(R.id.xrv_list)
    XRecyclerView xrv_list;
    @BindView(R.id.tv_newAddress)
    TextView tv_newAddress;

    AddressManagerAdapter managerAdapter;

    @Override
    protected AddressManagerActivityPresenter loadPresenter() {
        return new AddressManagerActivityPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_newAddress.setOnClickListener(this);
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(AddressManagerActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        setTitleName("地址管理");
        xrv_list.setLoadingMoreEnabled(false);
        xrv_list.setPullRefreshEnabled(false);
        managerAdapter = new AddressManagerAdapter(mContext);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.tv_newAddress:
                SwitchActivityManager.startCreateAddressActivity(mContext);
                break;
        }
    }

    @Override
    public void showLoading() {
        showLoading();
    }

    @Override
    public void hideLoading() {
        hideLoading();
    }
}
