package com.qzxq.shop.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.qzxq.shop.R;
import com.qzxq.shop.activity.AddressManagerActivity;
import com.qzxq.shop.activity.CreateAddressActivity;
import com.qzxq.shop.activity.FeedBackActivity;
import com.qzxq.shop.activity.LoginActivity;
import com.qzxq.shop.activity.MainActivity;
import com.qzxq.shop.activity.ShopBuyDetailActivity;
import com.qzxq.shop.activity.webview.WebViewActivity;


/**
 * Created by zhuzhen on 2017/11/2.
 * 管理activity
 */

public class SwitchActivityManager {

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
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("mUrl", mUrl);
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
