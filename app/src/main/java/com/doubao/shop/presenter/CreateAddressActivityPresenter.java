package com.doubao.shop.presenter;


import com.doubao.shop.activity.CreateAddressActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.model.CreateAddressActivityModel;

/**
 * Created by zhuzhen
 */

public class CreateAddressActivityPresenter extends BasePresenter<CreateAddressActivityModel,CreateAddressActivity> {

    public void getSaveDetail(String id,String name,String phone,String detail,String provinceName,String provinceCode,String cityName,String cityCode,String countyName,String countyCode,String isDetail) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getSaveDetail(id,name,phone,detail,provinceName,provinceCode,cityName,cityCode,countyName,countyCode,isDetail,new CreateAddressActivityModel.SaveDetailInterFace() {
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

    public void getRegionList(String parentId) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getRegionList(parentId,new CreateAddressActivityModel.RegionListInterFace() {
            @Override
            public void getRegionSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().getRegionSuccess(s);
                }
            }

            @Override
            public void getRegionFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().getRegionFail(s);
                }
            }
        });
    }


    @Override
    public CreateAddressActivityModel loadModel() {
        return new CreateAddressActivityModel();
    }

}
