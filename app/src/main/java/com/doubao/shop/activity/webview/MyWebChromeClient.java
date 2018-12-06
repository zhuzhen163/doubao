package com.doubao.shop.activity.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;


/**
 * Created by zhuzhen
 * - 播放网络视频配置
 * - 上传图片(兼容)
 */
public class MyWebChromeClient extends WebChromeClient {

    private WebViewActivity mActivity;
    private IWebPageView mIWebPageView;

    public MyWebChromeClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        this.mActivity = (WebViewActivity) mIWebPageView;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        mIWebPageView.progressChanged(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        // 设置title
        mActivity.setTitle(title);
        this.title = title;
    }

    private String title = "";

    public String getTitle() {
        return title + " ";
    }

}
