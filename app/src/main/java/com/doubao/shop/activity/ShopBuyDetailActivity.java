package com.doubao.shop.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.adapter.ShopBuyDetailAdapter;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.entity.AddressDetailBean;
import com.doubao.shop.entity.ShopBuyDetailBean;
import com.doubao.shop.entity.SubmitOrderBean;
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.presenter.ShopBuyDetailActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.StringUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.ShopBuyDetailActivityView;
import com.doubao.shop.widget.KelaDialog;
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
    private TextView tv_shopTotal,tv_freight,tv_couponPrice,tv_name,tv_phone,tv_address,tv_default,tv_platformNum,tv_kelaMessage,tv_vipDiscount;
    private ImageView iv_query;
    private View mHeaderView = null;
    private ShopBuyDetailAdapter detailAdapter;
    private String type,addressId,couponId;
    private KelaDialog dialog;

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
        iv_query.setOnClickListener(this);
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
            tv_platformNum = (TextView) mHeaderView.findViewById(R.id.tv_platformNum);
            iv_query = (ImageView) mHeaderView.findViewById(R.id.iv_query);
            tv_kelaMessage = (TextView) mHeaderView.findViewById(R.id.tv_kelaMessage);
            tv_vipDiscount = (TextView) mHeaderView.findViewById(R.id.tv_vipDiscount);
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
                SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/ucenter/coupon","优惠券");
                break;
            case R.id.tv_payment:
                    ConfigUtils.setCartRefresh(true);
                    mPresenter.orderSubmit(type,addressId,couponId);
                break;
            case R.id.rl_noAddress:
                SwitchActivityManager.startAddressManagerActivity(ShopBuyDetailActivity.this,"1");
                break;
            case R.id.iv_query:
                if (dialog == null){
                    dialog = new KelaDialog(ShopBuyDetailActivity.this);
                }
                if (!dialog.isShowing()){
                    dialog.show();
                }
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
        try {
            JSONObject object = new JSONObject(s);
            if ("0".equals(object.getString("errno"))){
                SubmitOrderBean orderBean = AppUtils.parseJsonWithGson(s, SubmitOrderBean.class);
                if ("umbpay".equals(orderBean.getPayCode())){
                    SwitchActivityManager.loadUrl(ShopBuyDetailActivity.this,UrlHelper.WEB_URL+orderBean.getPayurl(),"");
                }else {
                    SwitchActivityManager.loadOrderUrl(ShopBuyDetailActivity.this,orderBean.getPayurl(),"");
                }
                SwitchActivityManager.exitActivity(ShopBuyDetailActivity.this);
            }else {
                ToastUtil.showLong(object.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
                    addressId = checkedAddress.getId();
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

                if (data.isAmpleAmountFlag()){
                    tv_kelaMessage.setVisibility(View.GONE);
                }else {
                    tv_kelaMessage.setVisibility(View.VISIBLE);
                    tv_kelaMessage.setText("此订单中还可支付"+data.getUsableAmount()+"克拉，但您的克拉余额不足，剩余"+data.getResidueAmount()+"克拉");
                }

                if (StringUtils.isNotBlank(data.getMemberType())){
                    tv_vipDiscount.setVisibility(View.VISIBLE);
                    tv_vipDiscount.setText("("+data.getMemberType()+"会员已享受"+data.getVipDisCountAmount()+"优惠"+")");
                }else {
                    tv_vipDiscount.setVisibility(View.GONE);
                }

                tv_actualPrice.setText("实付：￥"+data.getActualPrice());
                tv_platformNum.setText("-￥"+data.getDisCountAmount());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
