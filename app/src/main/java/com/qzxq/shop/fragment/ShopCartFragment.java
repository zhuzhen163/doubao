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
import com.qzxq.shop.tools.SwitchActivityManager;
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
    @BindView(R.id.tv_delete_num)
    TextView tv_delete_num;
    @BindView(R.id.ll_shop)
    LinearLayout ll_shop;
    @BindView(R.id.ll_delete)
    LinearLayout ll_delete;
    @BindView(R.id.tv_complete)
    TextView tv_complete;
    @BindView(R.id.tv_checkedGoodsCount)
    TextView tv_checkedGoodsCount;
    @BindView(R.id.tv_checkedGoodsAmount)
    TextView tv_checkedGoodsAmount;
    @BindView(R.id.ll_noCart)
    LinearLayout ll_noCart;
    @BindView(R.id.tv_goShop)
    TextView tv_goShop;
    public ToHomeCallBack homeCallBack;

    private ShopCartFragmentAdapter shopCartFragmentAdapter;
    private List<CartListBean> list = new ArrayList<>();

    public interface ToHomeCallBack{
        void toHomeCallBack();
    }

    public void setHomeCallBack(ToHomeCallBack homeCallBack) {
        this.homeCallBack = homeCallBack;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_shopcart;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtil.isNetworkConnected(mContext)) {
            list.clear();
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
                    buySelectAll("1");
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        CartListBean entity = list.get(i);
                        entity.setCheck(false);
                    }
                    buySelectAll("0");
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
                if (isSelectShop()){
                    SwitchActivityManager.startShopBuyDetailActivity(mContext,"","","cart");
                }
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
        tv_goShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeCallBack.toHomeCallBack();
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
        String deleteIds = "";
        Iterator<CartListBean> it = list.iterator();
        while(it.hasNext()){
            CartListBean entity = it.next();
            boolean checkDelete = entity.isCheckDelete();
            if (checkDelete){
                deleteIds+=entity.getProduct_id()+",";
            }
        }

        mFragmentPresenter.cartDelete(deleteIds);
    }

    /**
     * 全选购买
     * @return
     * @param isChecked
     */
    public void buySelectAll(String isChecked){
        String checkShop = "";
        Iterator<CartListBean> it = list.iterator();
        while(it.hasNext()){
            CartListBean entity = it.next();
            checkShop+=entity.getProduct_id()+",";
        }

        mFragmentPresenter.isCheck(isChecked,checkShop);
    }

    /**
     * 是否有选中的商品
     * @return
     */
    public boolean isSelectShop(){
        for (int i = 0; i <list.size() ; i++) {
            CartListBean cartListBean = list.get(i);
            if ("1".equals(cartListBean.getChecked())){
                return true;
            }
        }
        return false;
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
    public void checkAll(boolean checkAll) {
        cb_select.setChecked(checkAll);
    }

    @Override
    public void checkAllDelete(boolean checkAllDelete) {
        cb_select_delete.setChecked(checkAllDelete);
    }

    @Override
    public void isCheckShop(String isChecked, String productIds) {
        mFragmentPresenter.isCheck(isChecked,productIds);
    }


    @Override
    public void productNum(int number, String goods_id, String id, String product_id) {
        mFragmentPresenter.update(goods_id,id,product_id,number);
    }

    @Override
    public void updateSuccess(String s) {
        updateData(s);
    }

    @Override
    public void updateFail(String s) {
        ToastUtil.showLong(s);
    }

    @Override
    public void isCheckSuccess(String s) {
        updateData(s);
    }

    @Override
    public void isCheckFail(String s) {
        ToastUtil.showLong(s);
    }

    @Override
    public void deleteSuccess(String s) {
        updateData(s);
    }

    @Override
    public void deleteFail(String s) {
        ToastUtil.showLong(s);
    }

    @Override
    public void getListSuccess(String s) {
        updateData(s);
    }

    @Override
    public void getListFail(String s) {

    }

    private void updateData(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                ShopCartListBean bean = AppUtils.parseJsonWithGson(s, ShopCartListBean.class);
                ShopCartListBean.CartTotal cartTotal = bean.getData().getCartTotal();
                tv_checkedGoodsCount.setText("全选("+cartTotal.getCheckedGoodsCount()+")");
                tv_checkedGoodsAmount.setText("￥"+cartTotal.getCheckedGoodsAmount());
                list  = bean.getData().getCartList();
                shopCartFragmentAdapter.setDataList(list);
                shopCartFragmentAdapter.notifyDataSetChanged();
                if (list.size() == 0){
                    ll_noCart.setVisibility(View.VISIBLE);
                    ll_net_connect.setVisibility(View.GONE);
                }else {
                    ll_noCart.setVisibility(View.GONE);
                    ll_net_connect.setVisibility(View.VISIBLE);
                }
            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
