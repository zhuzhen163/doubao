package com.qzxq.shop.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.adapter.ShopBuyDetailAdapter;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.entity.ShopBuyDetailBean;
import com.qzxq.shop.presenter.ShopBuyDetailActivityPresenter;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.ShopBuyDetailActivityView;
import com.qzxq.shop.widget.xrecyclerview.XRecyclerView;

import org.json.JSONObject;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/11/26
* description: 商品购买详情
*/
public class ShopBuyDetailActivity extends BaseActivity<ShopBuyDetailActivityPresenter> implements ShopBuyDetailActivityView{

    @BindView(R.id.xrv_list)
    XRecyclerView xrv_list;

    private RelativeLayout rl_checkAddress;
    private LinearLayout ll_checkCoupon;
    private TextView tv_shopTotal,tv_freight,tv_couponPrice;
    private View mHeaderView = null;
    private ShopBuyDetailAdapter detailAdapter;

    @Override
    protected ShopBuyDetailActivityPresenter loadPresenter() {
        return new ShopBuyDetailActivityPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.checkCart();
    }

    @Override
    protected void initListener() {
        ll_checkCoupon.setOnClickListener(this);
        rl_checkAddress.setOnClickListener(this);
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(ShopBuyDetailActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        setTitleName("购买详情");
        detailAdapter = new ShopBuyDetailAdapter(mContext);
        xrv_list.setAdapter(detailAdapter);
        xrv_list.setLayoutManager(new LinearLayoutManager(mContext));

        initHeadView();
    }

    private void initHeadView(){
        if (mHeaderView == null){
            mHeaderView = View.inflate(mContext, R.layout.head_shop_buy_detail, null);
            xrv_list.addHeaderView(mHeaderView);

            rl_checkAddress = (RelativeLayout) mHeaderView.findViewById(R.id.rl_checkAddress);
            ll_checkCoupon = (LinearLayout) mHeaderView.findViewById(R.id.ll_checkCoupon);
            tv_shopTotal = (TextView) mHeaderView.findViewById(R.id.tv_shopTotal);
            tv_freight = (TextView) mHeaderView.findViewById(R.id.tv_freight);
            tv_couponPrice = (TextView) mHeaderView.findViewById(R.id.tv_couponPrice);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_buy_detail;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.rl_checkAddress:

                break;
            case R.id.ll_checkCoupon:
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
    public void checkSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                ShopBuyDetailBean bean = AppUtils.parseJsonWithGson(s, ShopBuyDetailBean.class);

                tv_shopTotal.setText("￥"+bean.getGoodsTotalPrice());
                tv_freight.setText("￥"+bean.getFreightPrice());
                tv_couponPrice.setText("-￥"+bean.getCouponPrice());
                detailAdapter.addAll(bean.getCheckedGoodsList());
            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void checkFail(String s) {
        ToastUtil.showLong(s);
    }
}
