package com.qzxq.shop.view;


import com.qzxq.shop.base.BaseView;

/**
* @author zhuzhen
* create at 2018/11/23
* description:
*/

public interface AddressManagerActivityView extends BaseView {

    void deleteSuccess(String s);
    void deleteFail(String s);

    void getListSuccess(String s);
    void getListFail(String s);
}
