package com.doubao.shop.activity.webview;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.SwitchActivityManager;

public class WebViewClickInterface {
    private Context context;

    public WebViewClickInterface(Context context) {
        this.context = context;
    }

    /**
     * 通用跳转url
     * @param currencyUrl
     */
    @JavascriptInterface
    public void currencyUrl(String currencyUrl) {
        if (currencyUrl.contains("member/recharge")){//会员充值
            SwitchActivityManager.loadUrl(context,currencyUrl+"?userId="+ConfigUtils.getUserId(),"会员充值");
        }else {
            SwitchActivityManager.loadUrl(context,currencyUrl,"详情");
        }
    }

    /**
     * 跳转搜索
     * @param url
     */
    @JavascriptInterface
    public void toSearch(String url) {
        SwitchActivityManager.searchWebViewActivity(context,url,"搜索");
    }

    /**
     * 跳转二级分类
     * @param url
     */
    @JavascriptInterface
    public void toSecondary(String url) {
        SwitchActivityManager.searchWebViewActivity(context,url,"分类");
    }

    /**
     * 跳转登录
     */
    @JavascriptInterface
    public void toLogin(){
        SwitchActivityManager.startLoginActivity(context);
    }

    /**
     * 跳转购物车
     */
    @JavascriptInterface
    public void toShopCart() {
        ConfigUtils.DETAIL_TO_OTHER = 1;
        SwitchActivityManager.startMainActivity(context);
    }

    /**
     * 去首页
     */
    @JavascriptInterface
    public void goHome(){
        ConfigUtils.DETAIL_TO_OTHER = 2;
        SwitchActivityManager.startMainActivity(context);
    }

    /**
     * 订单列表
     */
    @JavascriptInterface
    public void goOrderList(){
        SwitchActivityManager.exitActivity((Activity) context);
    }

    /**
     * 去付款
     * @param buyUrl
     */
    @JavascriptInterface
    public void goBuy(String buyUrl){
        SwitchActivityManager.loadUrl(context,buyUrl,"");
    }

    /**
     * 支付完成
     */
    @JavascriptInterface
    public void payFinish(){
        ConfigUtils.DETAIL_TO_OTHER = 1;
        SwitchActivityManager.startMainActivity(context);
    }

    /**
     * 查看订单
     * @param orderUrl
     */
    @JavascriptInterface
    public void viewOrder(String orderUrl){
        ConfigUtils.DETAIL_TO_OTHER = 1;
        SwitchActivityManager.loadUrl(context,orderUrl,"");
        SwitchActivityManager.exitActivity((Activity) context);
    }
}
