package com.qzxq.shop.view;


import com.qzxq.shop.base.BaseView;

/**
* @author zhuzhen
* create at 2018/11/23
* description:
*/

public interface FeedBackActivityView extends BaseView {

    void saveSuccess(String s);
    void saveFail(String s);
}
