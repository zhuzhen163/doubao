package com.qzxq.shop.activity;

import android.view.View;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.adapter.AddressManagerAdapter;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.entity.AddressManagerBean;
import com.qzxq.shop.presenter.AddressManagerActivityPresenter;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.AddressManagerActivityView;
import com.qzxq.shop.widget.xrecyclerview.XRecyclerView;

import org.json.JSONObject;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/11/20
* description:地址管理
*/
public class AddressManagerActivity extends BaseActivity<AddressManagerActivityPresenter> implements AddressManagerActivityView, View.OnClickListener{

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
    protected void onResume() {
        super.onResume();
        mPresenter.getAddressList();
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
        setShowLoading(true);
    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

    @Override
    public void getListSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                AddressManagerBean bean = AppUtils.parseJsonWithGson(s, AddressManagerBean.class);

                managerAdapter.setDataList(bean.getData());
            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getListFail(String s) {

    }
}
