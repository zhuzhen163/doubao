package com.qzxq.shop.presenter;


import com.qzxq.shop.activity.AddressManagerActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.model.AddressManagerActivityModel;

/**
 * Created by zhuzhen
 */

public class AddressManagerActivityPresenter extends BasePresenter<AddressManagerActivityModel,AddressManagerActivity> {


    @Override
    public AddressManagerActivityModel loadModel() {
        return new AddressManagerActivityModel();
    }
}
