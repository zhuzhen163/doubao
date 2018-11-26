package com.qzxq.shop.model;


import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

public class AddressManagerActivityModel {

    public void deleteAddress(String id,final DeleteAddressInterFace deleteAddressInterFace){

        Http.getHttpService(UrlHelper.BASE_URL).deleteAddress(id).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        deleteAddressInterFace.deleteSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        deleteAddressInterFace.deleteFail(e.msg);
                    }
                });

    }

    public interface DeleteAddressInterFace{
        void deleteSuccess(String s);
        void deleteFail(String s);
    }

    public void getAddressList(final AddressListInterFace listInterFace){

        Http.getHttpService(UrlHelper.BASE_URL).getAddressList().
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        listInterFace.getListSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        listInterFace.getListFail(e.msg);
                    }
                });

    }

    public interface AddressListInterFace{
        void getListSuccess(String s);
        void getListFail(String s);
    }
}
