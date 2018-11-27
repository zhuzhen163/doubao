package com.qzxq.shop.model;


import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

import okhttp3.RequestBody;

public class CreateAddressActivityModel {

    public void getSaveDetail(RequestBody body, final SaveDetailInterFace saveDetailInterFace){

        Http.getHttpService(UrlHelper.BASE_URL).getSaveDetail(body).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        saveDetailInterFace.saveDetailSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        saveDetailInterFace.saveDetailFail(e.msg);
                    }
                });

    }
    public interface SaveDetailInterFace{
        void saveDetailSuccess(String s);
        void saveDetailFail(String s);
    }


    public void getAddressDetail(RequestBody body,final AddressDetailInterFace detailInterFace){

        Http.getHttpService(UrlHelper.BASE_URL).getAddressDetail(body).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        detailInterFace.getDetailSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        detailInterFace.getDetailFail(e.msg);
                    }
                });

    }

    public interface AddressDetailInterFace{
        void getDetailSuccess(String s);
        void getDetailFail(String s);
    }
}
