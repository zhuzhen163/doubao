package com.qzxq.shop.activity.webview;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.qzxq.shop.tools.SwitchActivityManager;

public class WebViewClickInterface {
    private Context context;

    public WebViewClickInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void textClick(String type, String item_pk) {
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(item_pk)) {
            Toast.makeText(context, "----点击了文字", Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public void productDeatil(String detailUrl) {
        SwitchActivityManager.loadUrl(context,detailUrl,"商品详情");
    }
}
