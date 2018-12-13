package com.doubao.shop.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.adapter.ShopBuyDetailAdapter;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.entity.AddressDetailBean;
import com.doubao.shop.entity.ShopBuyDetailBean;
import com.doubao.shop.presenter.ShopBuyDetailActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.ShopBuyDetailActivityView;
import com.doubao.shop.widget.xrecyclerview.XRecyclerView;

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
    @BindView(R.id.tv_actualPrice)
    TextView tv_actualPrice;
    @BindView(R.id.tv_payment)
    TextView tv_payment;

    private RelativeLayout rl_checkAddress,rl_noAddress;
    private LinearLayout ll_checkCoupon;
    private TextView tv_shopTotal,tv_freight,tv_couponPrice,tv_name,tv_phone,tv_address,tv_default;
    private View mHeaderView = null;
    private ShopBuyDetailAdapter detailAdapter;
    private String type,addressId,couponId;

    @Override
    protected ShopBuyDetailActivityPresenter loadPresenter() {
        return new ShopBuyDetailActivityPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        type = getIntent().getStringExtra("type");
        addressId = getIntent().getStringExtra("addressId");
        couponId = getIntent().getStringExtra("couponId");

        addressId = ConfigUtils.getAddressId();
        mPresenter.checkCart(type,addressId,couponId);

    }

    @Override
    protected void initListener() {
        rl_noAddress.setOnClickListener(this);
        tv_payment.setOnClickListener(this);
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
        detailAdapter = new ShopBuyDetailAdapter(ShopBuyDetailActivity.this);
        xrv_list.setAdapter(detailAdapter);
        xrv_list.setLayoutManager(new LinearLayoutManager(ShopBuyDetailActivity.this));

        xrv_list.setPullRefreshEnabled(false);
        xrv_list.setLoadingMoreEnabled(false);
        initHeadView();
    }

    private void initHeadView(){
        if (mHeaderView == null){
            mHeaderView = View.inflate(ShopBuyDetailActivity.this, R.layout.head_shop_buy_detail, null);
            xrv_list.addHeaderView(mHeaderView);

            rl_noAddress = (RelativeLayout) mHeaderView.findViewById(R.id.rl_noAddress);
            tv_name = (TextView) mHeaderView.findViewById(R.id.tv_name);
            tv_phone = (TextView) mHeaderView.findViewById(R.id.tv_phone);
            tv_address = (TextView) mHeaderView.findViewById(R.id.tv_address);
            tv_default = (TextView) mHeaderView.findViewById(R.id.tv_default);
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
                SwitchActivityManager.startAddressManagerActivity(ShopBuyDetailActivity.this,"1");
                break;
            case R.id.ll_checkCoupon:
                break;
            case R.id.tv_payment:
                    mPresenter.orderSubmit(type,addressId,couponId);
                break;
            case R.id.rl_noAddress:
                SwitchActivityManager.startAddressManagerActivity(ShopBuyDetailActivity.this,"1");
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
    public void submitSuccess(String s) {

    }

    @Override
    public void submitFail(String s) {
        ToastUtil.showLong(s);
    }

    @Override
    public void checkSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                ShopBuyDetailBean bean = AppUtils.parseJsonWithGson(s, ShopBuyDetailBean.class);
                ShopBuyDetailBean.ShopBuy data = bean.getData();
                tv_shopTotal.setText("￥"+data.getGoodsTotalPrice());
                tv_freight.setText("￥"+data.getFreightPrice());
                tv_couponPrice.setText("-￥"+data.getCouponPrice());

                AddressDetailBean checkedAddress = bean.getData().getCheckedAddress();
                if (checkedAddress.getId() != null){
                    tv_name.setText(checkedAddress.getUserName());
                    tv_phone.setText(checkedAddress.getTelNumber());
                    tv_address.setText(checkedAddress.getFull_region());
                    if ("1".equals(checkedAddress.getIsDefault())){
                        tv_default.setVisibility(View.VISIBLE);
                    }else {
                        tv_default.setVisibility(View.GONE);
                    }
                    rl_checkAddress.setVisibility(View.VISIBLE);
                    rl_noAddress.setVisibility(View.GONE);
                }else {
                    rl_checkAddress.setVisibility(View.GONE);
                    rl_noAddress.setVisibility(View.VISIBLE);
                }
                tv_actualPrice.setText("实付：￥"+data.getActualPrice());
                detailAdapter.setDataList(data.getCheckedGoodsList());
                detailAdapter.notifyDataSetChanged();
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
