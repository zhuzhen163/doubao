package com.doubao.shop.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.adapter.ShopCartFragmentAdapter;
import com.doubao.shop.base.BaseFragment;
import com.doubao.shop.entity.CartListBean;
import com.doubao.shop.entity.ShopCartListBean;
import com.doubao.shop.presenter.ShopCartFragmentPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.NetworkUtil;
import com.doubao.shop.tools.StringUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.ShopCartFragmentView;
import com.doubao.shop.widget.xrecyclerview.XRecyclerView;

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
    @BindView(R.id.ll_none)
    LinearLayout ll_none;
    @BindView(R.id.ll_goLogin)
    LinearLayout ll_goLogin;
    @BindView(R.id.tv_goLogin)
    TextView tv_goLogin;
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
    protected void initData(Bundle savedInstanceState) {
//        if (NetworkUtil.isNetworkConnected(mContext)) {
//            list.clear();
//            mFragmentPresenter.getShopCartList();
//            iv_none.setVisibility(View.GONE);
//            ll_net_connect.setVisibility(View.VISIBLE);
//        } else {
//            iv_none.setVisibility(View.VISIBLE);
//            ll_net_connect.setVisibility(View.GONE);
//            ToastUtil.showLong(getString(R.string.network_error));
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initData(){
        if (NetworkUtil.isNetworkConnected(mContext)) {
            if (StringUtils.isNotBlank(ConfigUtils.getToken())){
                list.clear();
                mFragmentPresenter.getShopCartList();
                ll_none.setVisibility(View.GONE);
                ll_net_connect.setVisibility(View.VISIBLE);
                if (ll_goLogin.getVisibility() == View.VISIBLE){
                    ll_goLogin.setVisibility(View.GONE);
                }
            }else {
                ll_goLogin.setVisibility(View.VISIBLE);
                ll_net_connect.setVisibility(View.GONE);
            }
        } else {
            ll_none.setVisibility(View.VISIBLE);
            ll_net_connect.setVisibility(View.GONE);
            ll_goLogin.setVisibility(View.GONE);
            ToastUtil.showLong(getString(R.string.network_error));
        }
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
                    buySelectAll("1");
                }else {
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
                }else {
                    ToastUtil.showLong("您还没有选中要购买的商品");
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
        tv_goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startLoginActivity(mContext);
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
                if (cartTotal.getGoodsCount().equals(cartTotal.getCheckedGoodsCount())){
                    cb_select.setChecked(true);
                }else {
                    cb_select.setChecked(false);
                }
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
