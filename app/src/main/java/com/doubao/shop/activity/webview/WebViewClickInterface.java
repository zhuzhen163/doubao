package com.doubao.shop.activity.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;

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
    public void productDetail(String detailUrl) {
        SwitchActivityManager.loadUrl(context,detailUrl,"商品详情");
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
    @JavascriptInterface
    public void toLogin(){
        SwitchActivityManager.startLoginActivity(context);
    }
}
