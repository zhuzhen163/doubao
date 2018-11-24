package com.qzxq.shop.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.adapter.ShopCartFragmentAdapter;
import com.qzxq.shop.base.BaseFragment;
import com.qzxq.shop.entity.CartListBean;
import com.qzxq.shop.entity.ShopCartListBean;
import com.qzxq.shop.presenter.ShopCartFragmentPresenter;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.tools.NetworkUtil;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.ShopCartFragmentView;
import com.qzxq.shop.widget.xrecyclerview.XRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * 购物车
 * Created by zhuzhen
 */

public class ShopCartFragment extends BaseFragment<ShopCartFragmentPresenter> implements ShopCartFragmentView,ShopCartFragmentAdapter.CheckBoxCallback {

    @BindView(R.id.xrv_list)
    XRecyclerView xrv_list;
    @BindView(R.id.iv_none)
    ImageView iv_none;
    @BindView(R.id.cb_select)
    CheckBox cb_select;
    @BindView(R.id.tv_editor)
    TextView tv_editor;
    @BindView(R.id.tv_buy)
    TextView tv_buy;
    @BindView(R.id.ll_net_connect)
    LinearLayout ll_net_connect;


    @BindView(R.id.tv_Delete)
    TextView tv_Delete;
    @BindView(R.id.cb_select_delete)
    CheckBox cb_select_delete;
    @BindView(R.id.ll_shop)
    LinearLayout ll_shop;
    @BindView(R.id.ll_delete)
    LinearLayout ll_delete;
    @BindView(R.id.tv_complete)
    TextView tv_complete;

    private ShopCartFragmentAdapter shopCartFragmentAdapter;
    private List<CartListBean> list = new ArrayList<>();
    private List<String> buyList = new ArrayList<>();
    private List<String> deleteList = new ArrayList<>();

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_shopcart;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mFragmentPresenter.getShopCartList();
            iv_none.setVisibility(View.GONE);
            ll_net_connect.setVisibility(View.VISIBLE);
        } else {
            iv_none.setVisibility(View.VISIBLE);
            ll_net_connect.setVisibility(View.GONE);
            ToastUtil.showLong("请检查网络");
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void release() {

    }

    @Override
    protected ShopCartFragmentPresenter loadMPresenter() {
        return new ShopCartFragmentPresenter();
    }

    @Override
    protected void initListener() {
        cb_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_select.isChecked()){
                    for (int i = 0; i < list.size(); i++) {
                        CartListBean entity = list.get(i);
                        entity.setCheck(true);
                    }
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        CartListBean entity = list.get(i);
                        entity.setCheck(false);
                    }
                }
                shopCartFragmentAdapter.notifyDataSetChanged();
            }
        });

        cb_select_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_select_delete.isChecked()){
                    for (int i = 0; i < list.size(); i++) {
                        CartListBean entity = list.get(i);
                        entity.setCheckDelete(true);
                    }
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        CartListBean entity = list.get(i);
                        entity.setCheckDelete(false);
                    }
                }
                shopCartFragmentAdapter.notifyDataSetChanged();
            }
        });

        tv_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSelect();
            }
        });

        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buySelect();
            }
        });

        tv_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    shopCartFragmentAdapter.isDelete(true);
                    ll_shop.setVisibility(View.GONE);
                    ll_delete.setVisibility(View.VISIBLE);
                    shopCartFragmentAdapter.notifyDataSetChanged();
            }
        });
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopCartFragmentAdapter.isDelete(false);
                ll_shop.setVisibility(View.VISIBLE);
                ll_delete.setVisibility(View.GONE);
                shopCartFragmentAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    protected void initView() {
        xrv_list.setLoadingMoreEnabled(false);
        xrv_list.setPullRefreshEnabled(false);
        shopCartFragmentAdapter = new ShopCartFragmentAdapter(mContext);
        shopCartFragmentAdapter.setCheckBoxCallback(this);

        xrv_list.setAdapter(shopCartFragmentAdapter);
        xrv_list.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    /**
     * 删除所选
     */
    public void deleteSelect(){
        Iterator<CartListBean> it = list.iterator();
        while(it.hasNext()){
            CartListBean entity = it.next();
            boolean checkDelete = entity.isCheckDelete();
            if (checkDelete){
                deleteList.add(entity.getId());
                it.remove();
            }
        }
        cb_select_delete.setChecked(false);
        shopCartFragmentAdapter.setDataList(list);
        shopCartFragmentAdapter.notifyDataSetChanged();
    }

    /**
     * 购买所选
     * @return
     */
    public List<String> buySelect(){
        Iterator<CartListBean> it = list.iterator();
        while(it.hasNext()){
            CartListBean entity = it.next();
            boolean checkDelete = entity.isCheck();
            if (checkDelete){
                buyList.add(entity.getId());
            }
        }
        return buyList;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void checkAll(boolean checkAll) {
        cb_select.setChecked(checkAll);
    }

    @Override
    public void checkBoxCallback(boolean checkAllDelete) {
        cb_select_delete.setChecked(checkAllDelete);
    }

    @Override
    public void getListSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                ShopCartListBean bean = AppUtils.parseJsonWithGson(s, ShopCartListBean.class);

                shopCartFragmentAdapter.addAll(bean.getData().getCartList());
                shopCartFragmentAdapter.notifyDataSetChanged();
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
