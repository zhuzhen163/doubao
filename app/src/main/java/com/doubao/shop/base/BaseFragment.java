package com.doubao.shop.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.widget.LoadingDialog;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private View view;
    public Context mContext;
    public LayoutInflater mInflater;
    private Unbinder unbinder;
    private LoadingDialog loadingDialog;
    public P mFragmentPresenter;
    private WebView baseWebView;

    public BaseFragment() {
        super();
    }

    public BaseFragment(Bundle bd) {
        super();
        this.setArguments(bd);
    }
    protected abstract int getFragmentLayoutId();
    // 加载数据
    abstract protected void initData(Bundle savedInstanceState);
    //加载P
    protected abstract P loadMPresenter();

    abstract protected void initListener();
    abstract protected void initView();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentPresenter = loadMPresenter();
        if (mFragmentPresenter!=null){
            mFragmentPresenter.attachView(this);
        }
        if (loadingDialog==null){
            loadingDialog = new LoadingDialog(mContext);
            loadingDialog.setMessage("加载中");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(getFragmentLayoutId(),null);
        }
        if (view.getLayoutParams()==null){
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        unbinder=ButterKnife.bind(this,view);
        initView();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
        initListener();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
        if (mFragmentPresenter!=null){
            mFragmentPresenter.detachView();
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * loading显示与隐藏
     *
     * @param is_show
     */
    public void setShowLoading(boolean is_show) {
        if (is_show) {
            if (loadingDialog!=null){
                loadingDialog.show();
            }
        } else {
            if (loadingDialog!=null){
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
    public void initWebViewSetting(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient) {
        this.baseWebView = webView;
        disableAccessibility();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        //设置 缓存模式
        String user_agent = AppUtils.getUserAgent();
        webView.getSettings().setUserAgentString(user_agent);
        int skdInt = Build.VERSION.SDK_INT;
        if (skdInt <= 18) {
//            webView.getSettings().setSavePassword(false);
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
        webView.getSettings().setAppCacheEnabled(false);
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
