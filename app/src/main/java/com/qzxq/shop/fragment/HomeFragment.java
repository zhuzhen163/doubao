package com.qzxq.shop.fragment;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
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
import com.qzxq.shop.presenter.HomeFragmentPresenter;
import com.qzxq.shop.tools.ConfigUtils;
import com.qzxq.shop.tools.LogUtil;
import com.qzxq.shop.view.HomeFragmentView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 主页
 * Created by zhuzhen on 2017/11/2.
 */

public class HomeFragment extends BaseFragment<HomeFragmentPresenter> implements HomeFragmentView {

    @BindView(R.id.wv_home)
    WebView wv_home;
    private Map<String, String> extraHeaders;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected HomeFragmentPresenter loadMPresenter() {
        return new HomeFragmentPresenter();
    }

    @Override
    protected void initListener() {
    }


    @Override
    protected void initView() {
        initWebView();
    }

    private void initWebView(){
        wv_home.addJavascriptInterface(new WebViewClickInterface(getActivity()), "android");
        initWebViewSetting(wv_home, new MyWebViewClient(), new MyWebChromeClient());
        //清除webview的缓存
        CookieSyncManager.createInstance(getActivity());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieManager.getInstance().removeSessionCookie();
        CookieSyncManager.getInstance().sync();
        CookieSyncManager.getInstance().startSync();
        extraHeaders = new HashMap<>();
        extraHeaders.put("X-Nideshop-Token", ConfigUtils.getToken());
        if (wv_home != null) {
            wv_home.loadUrl(UrlHelper.WEB_URL+"?device=android", extraHeaders);
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
        setShowLoading(true);
    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

}
