package com.qzxq.shop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseFragment;
import com.qzxq.shop.presenter.MineFragmentPresenter;
import com.qzxq.shop.tools.StringUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.view.MineFragmentView;

import org.json.JSONObject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 * Created by zhuzhen
 */

public class MineFragment extends BaseFragment<MineFragmentPresenter> implements MineFragmentView {

    @BindView(R.id.civ_headImage)
    CircleImageView civ_headImage;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_ptMoney)
    TextView tv_ptMoney;

    @BindView(R.id.ll_myOrder)
    LinearLayout ll_myOrder;
    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;
    @BindView(R.id.ll_collect)
    LinearLayout ll_collect;
    @BindView(R.id.ll_footprint)
    LinearLayout ll_footprint;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    @BindView(R.id.ll_accountSafe)
    LinearLayout ll_accountSafe;
    @BindView(R.id.ll_custom)
    LinearLayout ll_custom;
    @BindView(R.id.ll_helpCenter)
    LinearLayout ll_helpCenter;
    @BindView(R.id.ll_feedback)
    LinearLayout ll_feedback;
    @BindView(R.id.ll_bindPhone)
    LinearLayout ll_bindPhone;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentPresenter.getUserAccount();
    }

    @Override
    protected void release() {

    }

    @Override
    protected MineFragmentPresenter loadMPresenter() {
        return new MineFragmentPresenter();
    }

    @Override
    protected void initListener() {
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startAddressManagerActivity(mContext,"0");
            }
        });
        ll_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startFeedBackActivity(mContext);
            }
        });
        ll_bindPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startLoginActivity(mContext);
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

    @Override
    public void getAccountSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String data = jsonObject.getString("data");
            if (StringUtils.isNotBlank(data)){
                tv_ptMoney.setText(data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAccountFail(String s) {

    }
}
