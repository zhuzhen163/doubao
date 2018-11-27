package com.qzxq.shop.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qzxq.shop.R;
import com.qzxq.shop.adapter.AddressManagerAdapter;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.entity.AddressManagerBean;
import com.qzxq.shop.presenter.AddressManagerActivityPresenter;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.AddressManagerActivityView;
import com.qzxq.shop.widget.DeleteAddressDialog;
import com.qzxq.shop.widget.xrecyclerview.XRecyclerView;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
* @author zhuzhen
* create at 2018/11/20
* description:地址管理
*/
public class AddressManagerActivity extends BaseActivity<AddressManagerActivityPresenter> implements AddressManagerActivityView,AddressManagerAdapter.DeleteCallBack,DeleteAddressDialog.DeleteDialog{

    @BindView(R.id.xrv_list)
    XRecyclerView xrv_list;
    @BindView(R.id.tv_newAddress)
    TextView tv_newAddress;

    private AddressManagerAdapter managerAdapter;
    private DeleteAddressDialog deleteAddressDialog;

    private int position;
    private String id;

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
        managerAdapter.setDeleteCallBack(this);
        xrv_list.setAdapter(managerAdapter);
        xrv_list.setLayoutManager(new LinearLayoutManager(mContext));

        deleteAddressDialog = new DeleteAddressDialog(mContext);
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
    public void delete() {
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("id",id);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        mPresenter.deleteAddress(body);
    }
}
