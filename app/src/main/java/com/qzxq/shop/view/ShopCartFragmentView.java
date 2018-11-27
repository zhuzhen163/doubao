package com.qzxq.shop.view;


import com.qzxq.shop.base.BaseView;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface ShopCartFragmentView extends BaseView {

    //是否选中
    void isCheckSuccess(String s);
    void isCheckFail(String s);

    //删除购物车
    void deleteSuccess(String s);
    void deleteFail(String s);

    //获取购物车列表
    void getListSuccess(String s);
    void getListFail(String s);
}
