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
import com.qzxq.shop.entity.ShopCartEntity;
import com.qzxq.shop.presenter.ShopCartFragmentPresenter;
import com.qzxq.shop.tools.NetworkUtil;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.ShopCartFragmentView;
import com.qzxq.shop.widget.xrecyclerview.XRecyclerView;

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
    private List<ShopCartEntity> list = new ArrayList<>();
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
            iv_none.setVisibility(View.GONE);
            xrv_list.setVisibility(View.VISIBLE);
        } else {
            iv_none.setVisibility(View.VISIBLE);
            xrv_list.setVisibility(View.GONE);
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
                        ShopCartEntity entity = list.get(i);
                        entity.setCheck(true);
                    }
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        ShopCartEntity entity = list.get(i);
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
                        ShopCartEntity entity = list.get(i);
                        entity.setCheckDelete(true);
                    }
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        ShopCartEntity entity = list.get(i);
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

        for (int i = 0; i < 15; i++) {
            ShopCartEntity entity = new ShopCartEntity();
            entity.setProductImg("http://ojyz0c8un.bkt.clouddn.com/b_1.jpg");
            entity.setProductName(i+"名称");
            entity.setProductNum(i+"");
            entity.setProductId(i+"");
            entity.setProductPrice("￥"+i);
            list.add(entity);
        }
        xrv_list.setAdapter(shopCartFragmentAdapter);
        xrv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        shopCartFragmentAdapter.addAll(list);
        shopCartFragmentAdapter.notifyDataSetChanged();
        xrv_list.refreshComplete();
    }

    /**
     * 删除所选
     */
    public void deleteSelect(){
        Iterator<ShopCartEntity> it = list.iterator();
        while(it.hasNext()){
            ShopCartEntity entity = it.next();
            boolean checkDelete = entity.isCheckDelete();
            if (checkDelete){
                deleteList.add(entity.getProductId());
                it.remove();
            }
        }
        shopCartFragmentAdapter.setDataList(list);
        shopCartFragmentAdapter.notifyDataSetChanged();
    }

    /**
     * 购买所选
     * @return
     */
    public List<String> buySelect(){
        Iterator<ShopCartEntity> it = list.iterator();
        while(it.hasNext()){
            ShopCartEntity entity = it.next();
            boolean checkDelete = entity.isCheck();
            if (checkDelete){
                buyList.add(entity.getProductId());
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
}
