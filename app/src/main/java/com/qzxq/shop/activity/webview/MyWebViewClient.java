package com.qzxq.shop.activity.webview;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qzxq.shop.tools.NetworkUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.TreeMap;


/**
 * Created by zhuzhen
 * 监听网页链接:
 * - 根据标识:打电话、发短信、发邮件
 * - 进度条的显示
 * - 添加javascript监听
 */
public class MyWebViewClient extends WebViewClient {

    private IWebPageView mIWebPageView;
    private WebViewActivity mActivity;

    public MyWebViewClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        mActivity = (WebViewActivity) mIWebPageView;

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        // 支付宝
        if (url.contains("scheme")) {
            handlerAlipay(url);
        }
        if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith("sms:") || url.startsWith(WebView.SCHEME_MAILTO)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mActivity.startActivity(intent);
            } catch (ActivityNotFoundException ignored) {
            }
            return true;
        }
        mIWebPageView.startProgress();
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (mActivity.mProgress90) {
            mIWebPageView.hindProgressBar();
        } else {
            mActivity.mPageFinish = true;
        }
        if (!NetworkUtil.isNetworkConnected(mActivity)) {
            mIWebPageView.hindProgressBar();
        }
        // html加载完成之后，添加监听点击js函数
        mIWebPageView.addClickListener();
        super.onPageFinished(view, url);
    }

    private boolean handlerAlipay(String url) {
        DealedUrl dealedUrl = dealUrl(url);
        final String finalParams = dealedUrl.params;
        if (TextUtils.isEmpty(finalParams)) return false;
        TreeMap<String, String> treeMap = getMapFromString(finalParams);
        if (!treeMap.containsKey("scheme")) return false;
        String schemeUrl = treeMap.get("scheme");
        try {
            schemeUrl = URLDecoder.decode(schemeUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(schemeUrl));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            List<ResolveInfo> resolves = mActivity.getPackageManager().queryIntentActivities(intent, 0);
            if (resolves.size() > 0) {
                ((Activity) mActivity).startActivityIfNeeded(intent, -1);
            }
            return true;
        } catch (Exception e) {
            // 防止没有安装的情况
            e.printStackTrace();
            return false;
        }
    }

    private static class DealedUrl {
        public String url;
        public String params;
    }

    private static DealedUrl dealUrl(String url) {
        DealedUrl dealedUrl = new DealedUrl();
        if (!url.contains("?")) {
            dealedUrl.url = url;
            dealedUrl.params = "";
            return dealedUrl;
        }
        String params = url.substring(url.indexOf("?") + 1);
        dealedUrl.url = url.substring(0, url.indexOf("?"));
        String[] results = params.split("&");
        StringBuilder specialParams = new StringBuilder();//该url特有参数
        for (String str : results) {
            if (str.split("=").length != 2) {
                continue;
            }
            String key = str.split("=")[0];
            specialParams.append(str).append("&");
        }
        if (specialParams.length() > 0) {
            specialParams.deleteCharAt(specialParams.length() - 1);
        }
        dealedUrl.params = specialParams.toString();
        return dealedUrl;
    }

    private static TreeMap<String, String> getMapFromString(String data) {
        TreeMap<String, String> reqMap = new TreeMap<>();
        if (TextUtils.isEmpty(data)) {
            return reqMap;
        }
        String[] array = data.split("&");
        for (String entry : array) {
            String[] parts = entry.split("=");
            if (parts.length < 2) {
                continue;
            }
            reqMap.put(parts[0], parts[1]);
        }
        return reqMap;
    }
}
