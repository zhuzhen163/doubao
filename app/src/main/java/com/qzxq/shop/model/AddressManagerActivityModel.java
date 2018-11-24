package com.qzxq.shop.model;


import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

public class AddressManagerActivityModel {

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
