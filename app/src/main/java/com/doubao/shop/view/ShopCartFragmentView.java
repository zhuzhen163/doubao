package com.doubao.shop.view;


import com.doubao.shop.base.BaseView;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface ShopCartFragmentView extends BaseView {

    //去支付前检查是否有下架商品
    void payBeforeSuccess(String s);
    void payBeforeFail(String s);

    //更新购物车
    void updateSuccess(String s);
    void updateFail(String s);

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
