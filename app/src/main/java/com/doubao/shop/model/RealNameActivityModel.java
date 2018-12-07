package com.doubao.shop.model;


import com.doubao.shop.application.ZApplication;
import com.doubao.shop.exception.ApiException;
import com.doubao.shop.http.Http;
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.subscriber.CommonSubscriber;
import com.doubao.shop.transformer.StrTransformer;
import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.RequestBody;

public class RealNameActivityModel {

    public void bindUser(String username,String idcard, final InterFace interFace){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("username",username);
        paramsMap.put("idcard",idcard);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

        Http.getHttpService(UrlHelper.BASE_URL).bindUser(body).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        interFace.realNameSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        interFace.realNameFail(e.msg);
                    }
                });

    }

    public interface InterFace{
        void realNameSuccess(String s);
        void realNameFail(String s);
    }
}
