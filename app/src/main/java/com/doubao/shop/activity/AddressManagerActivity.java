package com.doubao.shop.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.adapter.AddressManagerAdapter;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.entity.AddressManagerBean;
import com.doubao.shop.presenter.AddressManagerActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.AddressManagerActivityView;
import com.doubao.shop.widget.DeleteAddressDialog;
import com.doubao.shop.widget.xrecyclerview.XRecyclerView;

import org.json.JSONObject;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/11/20
* description:地址管理
*/
public class AddressManagerActivity extends BaseActivity<AddressManagerActivityPresenter> implements AddressManagerActivityView,AddressManagerAdapter.AdapterCallBack,DeleteAddressDialog.DeleteDialog{

    @BindView(R.id.xrv_list)
    XRecyclerView xrv_list;
    @BindView(R.id.tv_newAddress)
    TextView tv_newAddress;

    private AddressManagerAdapter managerAdapter;
    private DeleteAddressDialog deleteAddressDialog;

    private int position;
    private String id,type = "1";

    @Override
    protected AddressManagerActivityPresenter loadPresenter() {
        return new AddressManagerActivityPresenter();
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        managerAdapter.setType(type);
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
        managerAdapter = new AddressManagerAdapter(AddressManagerActivity.this);
        managerAdapter.setAdapterCallBack(this);

        xrv_list.setAdapter(managerAdapter);
        xrv_list.setLayoutManager(new LinearLayoutManager(AddressManagerActivity.this));

        deleteAddressDialog = new DeleteAddressDialog(AddressManagerActivity.this);
        deleteAddressDialog.setDeleteDialog(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.tv_newAddress:
                SwitchActivityManager.startCreateAddressActivity(AddressManagerActivity.this,"0");
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
    public void deleteSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
               managerAdapter.remove(position);
               managerAdapter.notifyDataSetChanged();
            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
            }
            if (deleteAddressDialog != null){
                deleteAddressDialog.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
            if (deleteAddressDialog != null){
                deleteAddressDialog.cancel();
            }
        }
    }

    @Override
    public void deleteFail(String s) {
        ToastUtil.showLong(s);
    }

    @Override
    public void getListSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                AddressManagerBean bean = AppUtils.parseJsonWithGson(s, AddressManagerBean.class);

                managerAdapter.setDataList(bean.getData());
                managerAdapter.notifyDataSetChanged();
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

    @Override
    public void deletePosition(int position, String id) {
        this.position = position;
        this.id = id;
        if (deleteAddressDialog != null){
            deleteAddressDialog.show();
        }

    }

    @Override
    public void clickItem(String addressId) {
        if ("1".equals(type)){//返回购物详情
            ConfigUtils.saveAddressId(addressId);
            SwitchActivityManager.exitActivity(AddressManagerActivity.this);
        }else {
            SwitchActivityManager.startCreateAddressActivity(AddressManagerActivity.this,addressId);
        }
    }

    @Override
    public void delete() {
        mPresenter.deleteAddress(id);
    }
}
