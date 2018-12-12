package com.doubao.shop.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.doubao.shop.R;
import com.doubao.shop.activity.AccountCenterActivity;
import com.doubao.shop.activity.AccountSafeActivity;
import com.doubao.shop.activity.AddressManagerActivity;
import com.doubao.shop.activity.BaseWebViewActivity;
import com.doubao.shop.activity.CreateAddressActivity;
import com.doubao.shop.activity.CustomServiceActivity;
import com.doubao.shop.activity.FeedBackActivity;
import com.doubao.shop.activity.LoginActivity;
import com.doubao.shop.activity.MainActivity;
import com.doubao.shop.activity.RealNameActivity;
import com.doubao.shop.activity.ShopBuyDetailActivity;


/**
 * Created by zhuzhen on 2017/11/2.
 * 管理activity
 */

public class SwitchActivityManager {
    /**
     * 实名认证
     * @param mContext
     */
    public static void startRealNameActivity(Context mContext){
        Intent intent = new Intent(mContext, RealNameActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }
    /**
     * 账户安全
     * @param mContext
     */
    public static void startAccountSafeActivity(Context mContext){
        Intent intent = new Intent(mContext, AccountSafeActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }
    /**
     * 客户服务
     * @param mContext
     */
    public static void startCustomServiceActivity(Context mContext){
        Intent intent = new Intent(mContext, CustomServiceActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }
    /**
     * 账户中心
     * @param mContext
     */
    public static void startAccountCenterActivity(Context mContext){
        Intent intent = new Intent(mContext, AccountCenterActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }
    /**
     * 商品购买详情
     * @param mContext
     */
    public static void startShopBuyDetailActivity(Context mContext,String addressId,String couponId,String type){
        Intent intent = new Intent(mContext, ShopBuyDetailActivity.class);
        intent.putExtra("addressId", addressId);
        intent.putExtra("couponId", couponId);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }
    /**
     * 登录
     * @param mContext
     */
    public static void startLoginActivity(Context mContext){
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }
    /**
     * 意见反馈
     * @param mContext
     */
    public static void startFeedBackActivity(Context mContext){
        Intent intent = new Intent(mContext, FeedBackActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }
    /**
     * 新建地址
     * @param mContext
     * @param id
     */
    public static void startCreateAddressActivity(Context mContext, String id){
        Intent intent = new Intent(mContext, CreateAddressActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }

    /**
     * 地址管理
     * @param mContext
     * @param type 区分“购物车：1”进入还是从“我的：0”进入
     */
    public static void startAddressManagerActivity(Context mContext,String type){
        Intent intent = new Intent(mContext, AddressManagerActivity.class);
        intent.putExtra("type",type);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }

    /**
     * 打开网页:
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   title
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle) {
        Intent intent = new Intent(mContext, BaseWebViewActivity.class);
        //拼接device是通知h5加载链接来自哪个设备
        if (mUrl.contains("?")){
            intent.putExtra("mUrl", mUrl+"&device=android");
        }else {
            intent.putExtra("mUrl", mUrl+"?device=android");
        }
        intent.putExtra("mTitle", mTitle);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }

    /**
     * 主页
     * @param mContext
     */
    public static void startMainActivity(Context mContext){
        Intent in = new Intent(mContext,  MainActivity.class);
        mContext.startActivity(in);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }

    /**
     * 退出页面
     * @param activity
     */
    public static void exitActivity(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.left_out_two, R.anim.left_in_two);
    }

}
