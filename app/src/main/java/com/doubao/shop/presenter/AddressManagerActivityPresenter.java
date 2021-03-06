package com.doubao.shop.presenter;


import com.doubao.shop.activity.AddressManagerActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.model.AddressManagerActivityModel;

/**
 * Created by zhuzhen
 */

public class AddressManagerActivityPresenter extends BasePresenter<AddressManagerActivityModel,AddressManagerActivity> {


    public void deleteAddress(String id) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().deleteAddress(id, new AddressManagerActivityModel.DeleteAddressInterFace() {
            @Override
            public void deleteSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().deleteSuccess(s);
                }
            }

            @Override
            public void deleteFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().deleteFail(s);
                }
            }
        });
    }

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
