package com.doubao.shop.view;


import com.doubao.shop.base.BaseView;

/**
* @author zhuzhen
* create at 2018/11/23
* description:
*/

public interface CreateAddressActivityView extends BaseView {

    void saveDetailSuccess(String s);
    void saveDetailFail(String s);

    void getDetailSuccess(String s);
    void getDetailFail(String s);

    void getRegionSuccess(String s);
    void getRegionFail(String s);
}
