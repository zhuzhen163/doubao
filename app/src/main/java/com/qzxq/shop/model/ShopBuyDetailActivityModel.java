package com.qzxq.shop.model;


import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

import okhttp3.RequestBody;

public class ShopBuyDetailActivityModel {


    public void checkCart(RequestBody body,final CheckCartInterFace cartInterFace){

        Http.getHttpService(UrlHelper.BASE_URL).checkCart(body).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        cartInterFace.checkSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        cartInterFace.checkFail(e.msg);
                    }
                });

    }

    public interface CheckCartInterFace{
        void checkSuccess(String s);
        void checkFail(String s);
    }
}
