package com.doubao.shop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseFragment;
import com.doubao.shop.entity.AccountBean;
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.presenter.MineFragmentPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
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
    @BindView(R.id.rl_waitPayment)
    RelativeLayout rl_waitPayment;
    @BindView(R.id.rl_orderOk)
    RelativeLayout rl_orderOk;
    @BindView(R.id.rl_waitInGoods)
    RelativeLayout rl_waitInGoods;
    @BindView(R.id.rl_orderCancel)
    RelativeLayout rl_orderCancel;
    @BindView(R.id.tv_orderAll)
    TextView tv_orderAll;
    @BindView(R.id.tv_num_waitPayment)
    TextView tv_num_waitPayment;
    @BindView(R.id.tv_num_waitInGoods)
    TextView tv_num_waitInGoods;
    @BindView(R.id.tv_num_orderOk)
    TextView tv_num_orderOk;
    @BindView(R.id.tv_num_orderCancel)
    TextView tv_num_orderCancel;
    @BindView(R.id.ll_kelaNum)
    LinearLayout ll_kelaNum;
    @BindView(R.id.ll_noLogin)
    LinearLayout ll_noLogin;
    @BindView(R.id.rl_yesLogin)
    RelativeLayout rl_yesLogin;
    @BindView(R.id.tv_goLogin)
    TextView tv_goLogin;
    @BindView(R.id.tv_vip)
    TextView tv_vip;
    @BindView(R.id.ll_vplus)
    LinearLayout ll_vplus;
    @BindView(R.id.iv_vplus)
    ImageView iv_vplus;
    @BindView(R.id.tv_value)
    TextView tv_value;
    private String token;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    public void initData() {
        if (NetworkUtil.isNetworkConnected(mContext)){
            token = ConfigUtils.getToken();
            mFragmentPresenter.getUserAccount();
        }else {
            ToastUtil.showLong(getString(R.string.network_error));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        token = ConfigUtils.getToken();
        //登录或者退出登录刷新接口
        if (ConfigUtils.getAccountRefresh()){
            ConfigUtils.setAccountRefresh(false);
            mFragmentPresenter.getUserAccount();
        }
        if (StringUtils.isNotBlank(token)){
            setUser(true);
        }else {
            setUser(false);
        }
    }

    /**
     * 是否是登录用户
     */
    private void setUser(boolean login){
        if (login){
            rl_yesLogin.setVisibility(View.VISIBLE);
            ll_noLogin.setVisibility(View.GONE);
            ll_kelaNum.setVisibility(View.VISIBLE);
        }else {
            rl_yesLogin.setVisibility(View.GONE);
            ll_noLogin.setVisibility(View.VISIBLE);
            ll_kelaNum.setVisibility(View.GONE);
        }
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
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.startAccountCenterActivity(mContext);
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        ll_customService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startCustomServiceActivity(mContext);
            }
        });
        tv_goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startLoginActivity(mContext);
            }
        });
        rl_waitPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.startOrderStateActivity(mContext,1);
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        rl_waitInGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.startOrderStateActivity(mContext,2);
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        rl_orderOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.startOrderStateActivity(mContext,3);
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        rl_orderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.startOrderStateActivity(mContext,4);
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        tv_orderAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SwitchActivityManager.loadUrl(mContext,UrlHelper.WEB_URL+"/pages/ucenter/order","全部");
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.startOrderStateActivity(mContext,0);
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        ll_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/ucenter/coupon","优惠券");
//                    SwitchActivityManager.loadOrderUrl(mContext,"https://shouyin.yeepay.com/nc-cashier-wap/wap/query/result?token=8f1b3d47-f642-455c-ac60-1bfce2e05081","");
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        ll_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/ucenter/collect","我的收藏");
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        ll_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/ucenter/footprint","我的浏览");
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        ll_kelaNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/ucenter/amountMoney","克拉余额");
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        tv_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/member/memberCenter?vplus=0&userName="+ConfigUtils.getPhone(),"VIP会员");
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        ll_vplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/member/memberCenter?vplus=1&userName="+ConfigUtils.getPhone(),"VPLUS会员");
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
            }
        });
        tv_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNotBlank(token)){
                    SwitchActivityManager.loadUrl(mContext, UrlHelper.WEB_URL+"/pages/member/memberCenter?vplus=0&userName="+ConfigUtils.getPhone(),"VIP会员");
                }else {
                    SwitchActivityManager.startLoginActivity(mContext);
                }
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
            AccountBean bean = AppUtils.parseJsonWithGson(s, AccountBean.class);
            if (bean.getErrno() != null && !"1".equals(bean.getErrno())){
                ToastUtil.showLong(bean.getErrmsg());
                tv_num_waitPayment.setVisibility(View.GONE);
                tv_num_waitInGoods.setVisibility(View.GONE);
                tv_num_orderOk.setVisibility(View.GONE);
                tv_num_orderCancel.setVisibility(View.GONE);
                setUser(false);
            }else if ("1".equals(bean.getCode())){
                String data = bean.getData();
                if (StringUtils.isNotBlank(data)){
                    tv_ptMoney.setText(data);
                }

                if (bean.getMemberInfo() != null){
                    AccountBean.MemberInfo memberInfo = bean.getMemberInfo();
                    if (memberInfo.getVipGrade() != null){
                        tv_vip.setText(memberInfo.getVipGrade());
                    }else {
                        tv_vip.setText("未开通");
                    }
                    tv_value.setText("成长值"+memberInfo.getMembers_growth_value());

                    if (memberInfo.getVplusGrade() != null){
                        if ("1".equals(memberInfo.getIs_vplus())){
                            if (memberInfo.getVplusGrade().contains("白银")){
                                iv_vplus.setBackgroundResource(R.drawable.icon_yin);
                            }else if (memberInfo.getVplusGrade().contains("黄金")){
                                iv_vplus.setBackgroundResource(R.drawable.icon_jin);
                            }else if (memberInfo.getVplusGrade().contains("铂金")){
                                iv_vplus.setBackgroundResource(R.drawable.icon_bo);
                            }
                        }else {
                            if (memberInfo.getVplusGrade().contains("白银")){
                                iv_vplus.setBackgroundResource(R.drawable.icon_noyin);
                            }else if (memberInfo.getVplusGrade().contains("黄金")){
                                iv_vplus.setBackgroundResource(R.drawable.icon_nojin);
                            }else if (memberInfo.getVplusGrade().contains("铂金")){
                                iv_vplus.setBackgroundResource(R.drawable.icon_nobo);
                            }
                        }
                    }else {
                        iv_vplus.setBackgroundResource(R.drawable.icon_novplus);
                    }
                }else {
                    iv_vplus.setBackgroundResource(R.drawable.icon_novplus);
                    tv_value.setText("成长值0");
                    tv_vip.setText("未开通");
                }

                setOrderMark(bean);

                tv_name.setText(AppUtils.phoneEncrypt(ConfigUtils.getPhone()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置订单角标
     * @param bean
     */
    private void setOrderMark(AccountBean bean) {
        String unPaymentNum = bean.getUnPaymentNum();
        if (unPaymentNum != null){
            int int_unPaymentNum = Integer.parseInt(unPaymentNum);
            if (int_unPaymentNum > 0){
                tv_num_waitPayment.setVisibility(View.VISIBLE);
                tv_num_waitPayment.setText(unPaymentNum);
                if (int_unPaymentNum > 99){
                    tv_num_waitPayment.setText("...");
                }
            }else {
                tv_num_waitPayment.setVisibility(View.GONE);
            }
        }

        String deliveredNum = bean.getDeliveredNum();
        if (deliveredNum != null){
            int int_deliveredNum = Integer.parseInt(deliveredNum);
            if (int_deliveredNum > 0){
                tv_num_waitInGoods.setVisibility(View.VISIBLE);
                tv_num_waitInGoods.setText(deliveredNum);
                if (int_deliveredNum > 99){
                    tv_num_waitInGoods.setText("...");
                }
            }else {
                tv_num_waitInGoods.setVisibility(View.GONE);
            }
        }
        String successOrderNum = bean.getSuccessOrderNum();
        if (successOrderNum != null){
            int int_successOrderNum = Integer.parseInt(successOrderNum);
            if (int_successOrderNum > 0){
                tv_num_orderOk.setVisibility(View.VISIBLE);
                tv_num_orderOk.setText(successOrderNum);
                if (int_successOrderNum > 99){
                    tv_num_orderOk.setText("...");
                }
            }else {
                tv_num_orderOk.setVisibility(View.GONE);
            }
        }
        String cancelOrderNum = bean.getCancelOrderNum();
        if (cancelOrderNum != null){
            int int_cancelOrderNum = Integer.parseInt(cancelOrderNum);
            if (int_cancelOrderNum > 0){
                tv_num_orderCancel.setVisibility(View.VISIBLE);
                tv_num_orderCancel.setText(cancelOrderNum);
                if (int_cancelOrderNum > 99){
                    tv_num_orderCancel.setText("...");
                }
            }else {
                tv_num_orderCancel.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getAccountFail(String s) {

    }
}
