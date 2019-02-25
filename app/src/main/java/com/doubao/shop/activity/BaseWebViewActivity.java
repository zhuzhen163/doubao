package com.doubao.shop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.doubao.shop.R;
import com.doubao.shop.activity.webview.WebViewClickInterface;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.LogUtil;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import anet.channel.util.StringUtils;
import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/12/7
* description:
*/
public class BaseWebViewActivity extends BaseActivity {

    private static final String TAG = BaseWebViewActivity.class.getSimpleName();
    @BindView(R.id.webview)
    WebView webview;
    //处理加载超时机制 不能让用户在网络不好的情况下 一直加载 影响用户使用
    private Handler handler = new Handler();
    private Timer timer;
    private TimerTask timerTask;
    private String mTitle = "",type ="";
    private String loadUrl = "";//加载的url
    private boolean isLoad = true;
    private boolean miLoad = true;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.hideInputMethod(BaseWebViewActivity.this);
                SwitchActivityManager.exitActivity(BaseWebViewActivity.this);

            }
        });
    }

    @Override
    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loadUrl = getIntent().getStringExtra("mUrl");
        mTitle = getIntent().getStringExtra("mTitle");
        type = getIntent().getStringExtra("type");
        initWebViewSetting(webview, new MyWebViewClient(), new MyWebChromeClient());
        webview.addJavascriptInterface(new WebViewClickInterface(BaseWebViewActivity.this),"android");
        clearCookie();

        registerTimer();
    }

    /**
     * //清除webview的缓存
     */
    public void clearCookie() {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieManager.getInstance().removeSessionCookie();
        CookieSyncManager.getInstance().sync();
        CookieSyncManager.getInstance().startSync();
    }

    @Override
    protected int getLayoutId() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return R.layout.base_webview_ctivity;
    }

    @Override
    protected void otherViewClick(View view) {
    }

    @Override
    public void showLoading() {
        setShowLoading(true);
    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if ( webview != null){
            if ("1".equals(type)){
                webview.loadUrl(loadUrl);
            }else {
                webview.loadUrl(loadUrl+"&token="+ConfigUtils.getToken());
                if (AppUtils.isMIUI()){//兼容小米登录刷新
                    webview.reload();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //注意：super句话一定要删除，或者注释掉，否则又走handler.cancel()默认的不支持https的了。
        // super.onReceivedSslError(view, handler, error);
            handler.proceed();
            setShowLoading(false);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            setShowLoading(false);
            view.loadUrl("file:///android_asset/load_error.html");
        }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            LogUtil.i(TAG, "shouldOverrideUrlLoading:" + url);
//            loadUrl = url;
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            setShowLoading(true);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle();
            if (!title.contains("http") || !title.contains(".com") || !title.contains(".html")){
                setTitleName(view.getTitle());
            }
            if (StringUtils.isNotBlank(url) && url.contains("payment/successPay")){
                if (isLoad){
                    isLoad = false;
                    view.loadUrl(url+"&device=android&token="+ConfigUtils.getToken());
                }
            }
            if (AppUtils.isMIUI()){//兼容小米登录刷新
                if (!miLoad){
                    webview.reload();
                }
                miLoad = !miLoad;
            }
            setShowLoading(false);
            if (handler != null) {
                handler.removeCallbacks(runnable);
                timer.cancel();
                timer.purge();
                handler = null;
            }
        }
    }

    /**
     * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
     */
    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (TextUtils.isEmpty(mTitle)) {
                if (!title.contains(".com") || !title.contains("http") || !title.contains(".html")){
                    setTitleName(title);
                }
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    private void registerTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
                timer.cancel();
                timer.purge();
            }
        };
        timer.schedule(timerTask, 20000, 1);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (baseWebView != null) {
                int loadProgress = baseWebView.getProgress();
                if (loadProgress < 20) {
                    ToastUtil.showLong("网络加载超时,请稍后再试");
                    baseWebView.stopLoading();
                }
                setShowLoading(false);
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {//点击的是返回键
            AppUtils.hideInputMethod(BaseWebViewActivity.this);
            SwitchActivityManager.exitActivity(BaseWebViewActivity.this);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if( webview!=null) {
            ViewParent parent = webview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webview);
            }
            webview.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webview.getSettings().setJavaScriptEnabled(false);
            webview.clearHistory();
            webview.clearView();
            webview.removeAllViews();
            webview.loadUrl("about:blank");
            webview.destroy();
            webview = null;
        }
        if (timer != null){
            timer.cancel();
            timer =null;
        }
        if (timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }
        super.onDestroy();
    }

}
