package com.qzxq.shop.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qzxq.shop.R;
import com.qzxq.shop.adapter.ShopBuyDetailAdapter;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.entity.AddressDetailBean;
import com.qzxq.shop.entity.ShopBuyDetailBean;
import com.qzxq.shop.presenter.ShopBuyDetailActivityPresenter;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.ShopBuyDetailActivityView;
import com.qzxq.shop.widget.xrecyclerview.XRecyclerView;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import okhttp3.RequestBody;

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

    private RelativeLayout rl_checkAddress;
    private LinearLayout ll_checkCoupon;
    private TextView tv_shopTotal,tv_freight,tv_couponPrice,tv_name,tv_phone,tv_address;
    private ImageView iv_default_pic;
    private View mHeaderView = null;
    private ShopBuyDetailAdapter detailAdapter;

    @Override
    protected ShopBuyDetailActivityPresenter loadPresenter() {
        return new ShopBuyDetailActivityPresenter();
    }

    @Override
    protected void initData() {
        String type = getIntent().getStringExtra("type");
        String addressId = getIntent().getStringExtra("addressId");
        String couponId = getIntent().getStringExtra("couponId");

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("type",type);
        paramsMap.put("addressId",addressId);
        paramsMap.put("couponId",couponId);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        mPresenter.checkCart(body);
    }

    @Override
    protected void initListener() {
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
        detailAdapter = new ShopBuyDetailAdapter(mContext);
        xrv_list.setAdapter(detailAdapter);
        xrv_list.setLayoutManager(new LinearLayoutManager(mContext));

        xrv_list.setPullRefreshEnabled(false);
        xrv_list.setLoadingMoreEnabled(false);
        initHeadView();
    }

    private void initHeadView(){
        if (mHeaderView == null){
            mHeaderView = View.inflate(mContext, R.layout.head_shop_buy_detail, null);
            xrv_list.addHeaderView(mHeaderView);

            tv_name = (TextView) mHeaderView.findViewById(R.id.tv_name);
            tv_phone = (TextView) mHeaderView.findViewById(R.id.tv_phone);
            tv_address = (TextView) mHeaderView.findViewById(R.id.tv_address);
            iv_default_pic = (ImageView) mHeaderView.findViewById(R.id.iv_default_pic);
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
                SwitchActivityManager.startAddressManagerActivity(mContext,"1");
                break;
            case R.id.ll_checkCoupon:
                break;
            case R.id.tv_payment:

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
                ShopBuyDetailBean.ShopBuy data = bean.getData();
                tv_shopTotal.setText("￥"+data.getGoodsTotalPrice());
                tv_freight.setText("￥"+data.getFreightPrice());
                tv_couponPrice.setText("-￥"+data.getCouponPrice());

                AddressDetailBean checkedAddress = bean.getData().getCheckedAddress();
                tv_name.setText(checkedAddress.getUserName());
                tv_phone.setText(checkedAddress.getTelNumber());
                tv_address.setText(checkedAddress.getFull_region());
                if ("1".equals(checkedAddress.getIsDefault())){
                    iv_default_pic.setVisibility(View.VISIBLE);
                }else {
                    iv_default_pic.setVisibility(View.GONE);
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
