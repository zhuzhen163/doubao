package com.qzxq.shop.view;


import com.qzxq.shop.base.BaseView;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface ShopCartFragmentView extends BaseView {

    //获取购物车列表
    void getListSuccess(String s);
    void getListFail(String s);
}
