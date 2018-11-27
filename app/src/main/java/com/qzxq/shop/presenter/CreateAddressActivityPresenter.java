package com.qzxq.shop.presenter;


import com.qzxq.shop.activity.CreateAddressActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.model.CreateAddressActivityModel;

/**
 * Created by zhuzhen
 */

public class CreateAddressActivityPresenter extends BasePresenter<CreateAddressActivityModel,CreateAddressActivity> {

    public void getSaveDetail(String id,String name,String phone,String detail,String provinceName,String cityName,String countyName,String isDetail) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getSaveDetail(id,name,phone,detail,provinceName,cityName,countyName,isDetail,new CreateAddressActivityModel.SaveDetailInterFace() {
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

    public void getAddressDetail(String id) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getAddressDetail(id,new CreateAddressActivityModel.AddressDetailInterFace() {
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
