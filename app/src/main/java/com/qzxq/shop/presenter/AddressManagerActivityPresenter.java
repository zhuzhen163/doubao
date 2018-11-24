package com.qzxq.shop.presenter;


import com.qzxq.shop.activity.AddressManagerActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.model.AddressManagerActivityModel;

/**
 * Created by zhuzhen
 */

public class AddressManagerActivityPresenter extends BasePresenter<AddressManagerActivityModel,AddressManagerActivity> {


    public void getAddressList() {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getAddressList(new AddressManagerActivityModel.AddressListInterFace() {
            @Override
            public void getListSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().getListSuccess(s);
                }
            }

            @Override
            public void getListFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().getListFail(s);
                }
            }
        });
    }

    @Override
    public AddressManagerActivityModel loadModel() {
        return new AddressManagerActivityModel();
    }


}
