package com.doubao.shop.presenter;


import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.fragment.ShopCartFragment;
import com.doubao.shop.model.ShopCartFragmentModel;

/**
 * Created by zhuzhen
 */

public class ShopCartFragmentPresenter extends BasePresenter<ShopCartFragmentModel,ShopCartFragment> {

    public void payBeforeCheck(String goodIds){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().payBeforeCheck(goodIds,new ShopCartFragmentModel.PayBeforeCallBack() {
            @Override
            public void payBeforeSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().payBeforeSuccess(s);
                }
            }

            @Override
            public void payBeforeFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().payBeforeFail(s);
                }
            }
        });
    }

    public void update(String goodsId,String id,String productId,int number){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().update(goodsId,id,productId,number,new ShopCartFragmentModel.UpdateCallBack() {
            @Override
            public void updateSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().updateSuccess(s);
                }
            }

            @Override
            public void updateFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().updateFail(s);
                }
            }
        });
    }

    public void isCheck(String isChecked,String checkShop){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().isCheck(isChecked,checkShop,new ShopCartFragmentModel.IsCheckCallBack() {
            @Override
            public void isCheckSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().isCheckSuccess(s);
                }
            }

            @Override
            public void isCheckFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().isCheckFail(s);
                }
            }
        });
    }


    public void cartDelete(String deleteIds){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().cartDelete(deleteIds,new ShopCartFragmentModel.DeleteCallBack() {
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

    public void getShopCartList(){
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().getShopCartList(new ShopCartFragmentModel.ShopCartList() {
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
    public ShopCartFragmentModel loadModel() {
        return new ShopCartFragmentModel();
    }
}
