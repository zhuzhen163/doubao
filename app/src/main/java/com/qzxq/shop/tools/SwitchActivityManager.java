package com.qzxq.shop.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.qzxq.shop.R;
import com.qzxq.shop.activity.AddressManagerActivity;
import com.qzxq.shop.activity.CreateAddressActivity;
import com.qzxq.shop.activity.FeedBackActivity;
import com.qzxq.shop.activity.webview.WebViewActivity;


/**
 * Created by zhuzhen on 2017/11/2.
 * 管理activity
 */

public class SwitchActivityManager {

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
     */
    public static void startCreateAddressActivity(Context mContext){
        Intent intent = new Intent(mContext, CreateAddressActivity.class);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.left_out, R.anim.left_in);
    }

    /**
     * 地址管理
     * @param mContext
     */
    public static void startMyAddressActivity(Context mContext){
        Intent intent = new Intent(mContext, AddressManagerActivity.class);
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
     * 退出页面
     * @param activity
     */
    public static void exitActivity(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.left_out_two, R.anim.left_in_two);
    }

}
