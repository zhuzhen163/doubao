package com.doubao.shop.view;


import com.doubao.shop.base.BaseView;

/**
* @author zhuzhen
* create at 2018/11/23
* description:
*/

public interface ShopBuyDetailActivityView extends BaseView {

    //提交订单
    void submitSuccess(String s);
    void submitFail(String s);

    void checkSuccess(String s);
    void checkFail(String s);
}
