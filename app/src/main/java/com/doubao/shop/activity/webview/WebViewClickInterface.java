package com.doubao.shop.activity.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.doubao.shop.tools.SwitchActivityManager;

public class WebViewClickInterface {
    private Context context;

    public WebViewClickInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void productDeatil(String detailUrl) {
        SwitchActivityManager.loadUrl(context,detailUrl,"商品详情");
    }
}
