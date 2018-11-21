package com.qzxq.shop.presenter;


import com.qzxq.shop.activity.CreateAddressActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.model.CreateAddressActivityModel;

/**
 * Created by zhuzhen
 */

public class CreateAddressActivityPresenter extends BasePresenter<CreateAddressActivityModel,CreateAddressActivity> {


    @Override
    public CreateAddressActivityModel loadModel() {
        return new CreateAddressActivityModel();
    }
}
