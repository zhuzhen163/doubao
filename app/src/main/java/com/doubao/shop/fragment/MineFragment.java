package com.doubao.shop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseFragment;
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.presenter.MineFragmentPresenter;
import com.doubao.shop.tools.NetworkUtil;
import com.doubao.shop.tools.StringUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.MineFragmentView;

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
    @BindView(R.id.ll_accountCenter)
    LinearLayout ll_accountCenter;
    @BindView(R.id.ll_customService)
    LinearLayout ll_customService;
    @BindView(R.id.ll_messageCenter)
    LinearLayout ll_messageCenter;
    @BindView(R.id.ll_browse)
    LinearLayout ll_browse;
    @BindView(R.id.ll_collect)
    LinearLayout ll_collect;
    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    public void initData() {
        if (NetworkUtil.isNetworkConnected(mContext)){
            mFragmentPresenter.getUserAccount();
        }else {
            ToastUtil.showLong(getString(R.string.network_error));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected MineFragmentPresenter loadMPresenter() {
        return new MineFragmentPresenter();
    }

    @Override
    protected void initListener() {
        ll_accountCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startAccountCenterActivity(mContext);
            }
        });
        ll_customService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startCustomServiceActivity(mContext);
            }
        });
        civ_headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startLoginActivity(mContext);
            }
        });
        ll_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/ucenter/coupon","优惠券");
            }
        });
        ll_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/ucenter/collect","我的收藏");
            }
        });
        ll_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/ucenter/footprint","我的浏览");
            }
        });
    }

    @Override
    protected void initView() {

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
