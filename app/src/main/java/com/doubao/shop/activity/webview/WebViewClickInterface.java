package com.doubao.shop.activity.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.SwitchActivityManager;

public class WebViewClickInterface {
    private Context context;

    public WebViewClickInterface(Context context) {
        this.context = context;
    }

    /**
     * 跳转商品详情
     * @param detailUrl
     */
    @JavascriptInterface
    public void productDeatil(String detailUrl) {
        SwitchActivityManager.loadUrl(context,detailUrl,"商品详情");
    }

    /**
     * 跳转购物车
     */
    @JavascriptInterface
    public void toShopCart() {
        AppUtils.DETAIL_TO_CART = 1;
        SwitchActivityManager.startMainActivity(context);
    }

    /**
     * 跳转搜索
     * @param url
     */
    @JavascriptInterface
    public void toSearch(String url) {
        SwitchActivityManager.loadUrl(context,url,"搜索");
    }

    /**
     * 跳转二级分类
     * @param url
     */
    @JavascriptInterface
    public void toSecondary(String url) {
        SwitchActivityManager.loadUrl(context,url,"分类");
    }

    /**
     * 跳转登录
     */
    public void toLogin(){
        SwitchActivityManager.startLoginActivity(context);
    }
}
