package com.qzxq.shop.activity.webview;

/**
 * Created by zhuzhen
 */
public interface IWebPageView {

    // 隐藏进度条
    void hindProgressBar();

    //  进度条先加载到90%,然后再加载到100%
    void startProgress();

    /**
     * 进度条变化时调用
     */
    void progressChanged(int newProgress);

    /**
     * 添加js监听
     */
    void addClickListener();

}
