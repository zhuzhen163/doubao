package com.qzxq.shop.presenter;


import com.qzxq.shop.activity.CreateAddressActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.model.CreateAddressActivityModel;

/**
 * Created by zhuzhen
 */

public class CreateAddressActivityPresenter extends BasePresenter<CreateAddressActivityModel,CreateAddressActivity> {

    public void getSaveDetail(String id,String userName,String telNumber,String detailInfo,String is_default) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getSaveDetail(id,userName,telNumber,detailInfo,is_default,new CreateAddressActivityModel.SaveDetailInterFace() {
            @Override
            public void saveDetailSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().saveDetailSuccess(s);
                }
            }

            @Override
            public void saveDetailFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().saveDetailFail(s);
                }
            }
        });
    }

    public void getAddressDetail() {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getAddressDetail(new CreateAddressActivityModel.AddressDetailInterFace() {
            @Override
            public void getDetailSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().getDetailSuccess(s);
                }
            }

            @Override
            public void getDetailFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().getDetailFail(s);
                }
            }
        });
    }
    @Override
    public CreateAddressActivityModel loadModel() {
        return new CreateAddressActivityModel();
    }

}
