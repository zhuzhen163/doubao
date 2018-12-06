package com.doubao.shop.model;


import com.google.gson.Gson;
import com.doubao.shop.application.ZApplication;
import com.doubao.shop.exception.ApiException;
import com.doubao.shop.http.Http;
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.subscriber.CommonSubscriber;
import com.doubao.shop.transformer.StrTransformer;

import java.util.HashMap;

import okhttp3.RequestBody;

public class ShopBuyDetailActivityModel {


    public void checkCart(String type,String addressId,String couponId,final CheckCartInterFace cartInterFace){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("type",type);
        paramsMap.put("addressId",addressId);
        paramsMap.put("couponId",couponId);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

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
