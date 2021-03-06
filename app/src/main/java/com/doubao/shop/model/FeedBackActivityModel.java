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

public class FeedBackActivityModel {

    public void saveFeedBack(String phone,String index,String content, final SaveFeedBackInterFace interFace){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("mobile",phone);
        paramsMap.put("index",index);
        paramsMap.put("content",content);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

        Http.getHttpService(UrlHelper.BASE_URL).saveFeedBack(body).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        interFace.saveSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        interFace.saveFail(e.msg);
                    }
                });

    }

    public interface SaveFeedBackInterFace{
        void saveSuccess(String s);
        void saveFail(String s);
    }

}
