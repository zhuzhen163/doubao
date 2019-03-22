package com.doubao.shop.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.SwitchActivityManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/12/28
* description:订单详情
*/
public class OrderStateActivity extends BaseActivity {

    @BindView(R.id.tab_order)
    TabLayout tab_order;
    @BindView(R.id.webview)
    WebView webview;
    private int type = 0;

    private ArrayList<String> mTitleList = new ArrayList<>(5);
    private String joint = "?device=android&token="+ ConfigUtils.getToken();
    private String order = UrlHelper.WEB_URL+"/pages/ucenter/order"+joint;
    private String order1 = UrlHelper.WEB_URL+"/pages/ucenter/order1"+joint;//代付款
    private String order2 = UrlHelper.WEB_URL+"/pages/ucenter/order2"+joint;//待收货
    private String order3 = UrlHelper.WEB_URL+"/pages/ucenter/order3"+joint;//已完成
    private String order4 = UrlHelper.WEB_URL+"/pages/ucenter/order4"+joint;//已取消

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webview != null){
            if (0 == type){
                tab_order.getTabAt(0).select();
                webview.loadUrl(order);
            }else if (1 == type){
                tab_order.getTabAt(1).select();
                webview.loadUrl(order1);
            }else if (2 == type){
                tab_order.getTabAt(2).select();
                webview.loadUrl(order2);
            }else if (3 == type){
                tab_order.getTabAt(3).select();
                webview.loadUrl(order3);
            }else if (4 == type){
                tab_order.getTabAt(4).select();
                webview.loadUrl(order4);
            }
            if (AppUtils.isMIUI()){//兼容小米登录刷新
                webview.reload();
            }
        }
    }

    @Override
    protected void initListener() {
        tab_order.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    webview.loadUrl(order);
                }else if (tab.getPosition() == 1){
                    webview.loadUrl(order1);
                }else if (tab.getPosition() == 2){
                    webview.loadUrl(order2);
                }else if (tab.getPosition() == 3){
                    webview.loadUrl(order3);
                }else if (tab.getPosition() == 4){
                    webview.loadUrl(order4);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(OrderStateActivity.this);
            }
        });
    }

    @Override
    protected void initView() {

        type = getIntent().getIntExtra("type",0);

        mTitleList.add("全部");
        mTitleList.add("待付款");
        mTitleList.add("待收货");
        mTitleList.add("已完成");
        mTitleList.add("已取消");

        for (int i = 0; i < mTitleList.size(); i++) {
            TabLayout.Tab tab = tab_order.newTab();//创建tab
            tab.setText(mTitleList.get(i));//设置文字
            tab_order.addTab(tab);//添加到tabLayout中
        }

        initWebViewSetting(webview, new MyWebViewClient(), new MyWebChromeClient());
        webview.addJavascriptInterface(new WebViewClickInterface(OrderStateActivity.this),"android");
        clearCookie();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_state;
    }

    @Override
    protected void otherViewClick(View view) {

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
            if (url.contains("https://shouyin.yeepay.com")){
                SwitchActivityManager.loadOrderUrl(mContext,url,"");
                return true;
            }
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
            setShowLoading(false);
        }
    }

    /**
     * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
     */
    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            AppUtils.hideInputMethod(OrderStateActivity.this);
            SwitchActivityManager.exitActivity(OrderStateActivity.this);
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
        super.onDestroy();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
