package com.qzxq.shop.fragment;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qzxq.shop.R;
import com.qzxq.shop.activity.webview.WebViewClickInterface;
import com.qzxq.shop.base.BaseFragment;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.presenter.ClassifyFragmentPresenter;
import com.qzxq.shop.tools.LogUtil;
import com.qzxq.shop.view.ClassifyFragmentView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 分类
 * @author zhuzhen
 */
public class ClassifyFragment extends BaseFragment<ClassifyFragmentPresenter> implements ClassifyFragmentView {

    @BindView(R.id.wb_classify)
    WebView wb_classify;
    private Map<String, String> extraHeaders;
    private String loadUrl = "";

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected ClassifyFragmentPresenter loadMPresenter() {
        return new ClassifyFragmentPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        loadUrl = UrlHelper.WEB_URL+"pages/classification?device=android";
        initWebView();
    }

    private void initWebView(){
        wb_classify.addJavascriptInterface(new WebViewClickInterface(getActivity()), "android");
        initWebViewSetting(wb_classify, new MyWebViewClient(), new MyWebChromeClient());
        //清除webview的缓存
        CookieSyncManager.createInstance(getActivity());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieManager.getInstance().removeSessionCookie();
        CookieSyncManager.getInstance().sync();
        CookieSyncManager.getInstance().startSync();
        extraHeaders = new HashMap<>();
        extraHeaders.put("X-Nideshop-Token", "y7wd3mfteix1zu2hq37kzdf0ntj8gvwg");
        if (wb_classify != null && !TextUtils.isEmpty(loadUrl)) {
            wb_classify.loadUrl(loadUrl, extraHeaders);
        }
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
            setShowLoading(false);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            setShowLoading(false);
            view.loadUrl("file:///android_asset/load_error.html");
        }

        /**
         * 这个方法很重要 在这里拦截url进行处理
         * 重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
         */
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            LogUtil.i("shouldOverrideUrlLoading", url);
            view.loadUrl(url, extraHeaders);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            setShowLoading(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setShowLoading(false);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, final String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

}
