package com.doubao.shop.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.doubao.shop.R;
import com.doubao.shop.application.ZApplication;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.CommonUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.widget.LoadingDialog;
import com.doubao.shop.widget.PublicTitleView;
import com.doubao.shop.widget.statusbar.StatusBarUtil;

import java.lang.reflect.Method;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by zhuzhen on 2017/11/2.
 */

public abstract class BaseActivity <P extends BasePresenter>  extends FragmentActivity implements BaseView,View.OnClickListener{
    protected P mPresenter;
    public Context mContext;
    private LinearLayout content;
    private LoadingDialog loadingDialog;
    private int id = -1;
    private final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private Unbinder unbinder;
    public PublicTitleView base_title;
    public WebView baseWebView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_base);
        initApp();
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme),0);
    }
    public void initApp(){
        baseSetContentView();
        mContext = this;
        ZApplication.getAppContext().addActivity(this);
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(mContext);
        }
        mPresenter = loadPresenter();
        initCommonData();
        initView();
        initListener();
        initData();
    }
    public void baseSetContentView() {
        content = (LinearLayout) findViewById(R.id.content);
        base_title = (PublicTitleView) findViewById(R.id.base_title);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(getLayoutId(),content,true);
        unbinder= ButterKnife.bind(this,view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    protected abstract P loadPresenter();

    private void initCommonData() {
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void otherViewClick(View view);


    /**
     * 点击的事件的统一的处理
     * 防双击，避免多次查询或插入
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(isOpenNoDoubleClick()){
            long currentTime = Calendar.getInstance().getTimeInMillis();
            int mId = view.getId();
            if (id != mId) {
                id = mId;
                lastClickTime = currentTime;
                otherViewClick(view);
                return;
            }
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                otherViewClick(view);
            }
        }else{
            otherViewClick(view);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppUtils.hideInputMethod(BaseActivity.this);
            SwitchActivityManager.exitActivity(BaseActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected boolean isOpenNoDoubleClick(){
        return true;
    }

    public void setBaseTitleState(int state) {
        base_title.setVisibility(state);
    }

    public void setTitleName(String name) {
        base_title.setTitleName(name);
    }

    public void setBackListener(View.OnClickListener clickListener){
        base_title.setOnClickListener(clickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        //解除绑定
        if (unbinder!=null){
            unbinder.unbind();
        }
        ZApplication.getAppContext().removeActivity(this);
    }

    /**
     * loading显示与隐藏
     *
     * @param is_show
     */
    public void setShowLoading(boolean is_show) {
        if (is_show) {
            if (loadingDialog != null) {
                loadingDialog.show();
            }
        } else {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        }
    }

    /**
     * 初始化WebView
     *
     * @param webView
     * @param webViewClient
     * @param webChromeClient
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initWebViewSetting(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient) {
        this.baseWebView = webView;
        disableAccessibility();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        String user_agent = AppUtils.getUserAgent();
        webView.getSettings().setUserAgentString(user_agent);
        int skdInt = Build.VERSION.SDK_INT;
        if (skdInt <= 18) {
            webView.getSettings().setSavePassword(false);
        }
        if (skdInt >= 19) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        if (skdInt >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (skdInt >= 11) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
                return true;
            }
        });
    }
    /**
     * 关闭辅助功能，针对4.2.1和4.2.2 崩溃问题 java.lang.NullPointerException at
     * android.webkit.AccessibilityInjector$TextToSpeechWrapper$1.onInit(
     * AccessibilityInjector.java: 753) ... ... at
     * android.webkit.CallbackProxy.handleMessage(CallbackProxy.java:321)
     */
    private void disableAccessibility() {
        /*
         * 4.2
         * (Build.VERSION_CODES.JELLY_BEAN_MR1)
         */
        if (Build.VERSION.SDK_INT == 17) {
            try {
                AccessibilityManager am = (AccessibilityManager) mContext
                        .getSystemService(Context.ACCESSIBILITY_SERVICE);
                if (!am.isEnabled()) {
                    return;
                }
                Method set = am.getClass().getDeclaredMethod("setState", int.class);
                set.setAccessible(true);
                set.invoke(am, 0);
            } catch (Exception e) {
            }
        }
    }

}
