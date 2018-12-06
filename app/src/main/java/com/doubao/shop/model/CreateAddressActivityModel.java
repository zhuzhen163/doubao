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

public class CreateAddressActivityModel {

    public void getSaveDetail(String id,String name,String phone,String detail,String provinceName,String cityName,String countyName,String isDetail, final SaveDetailInterFace saveDetailInterFace){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("id",id);
        paramsMap.put("userName",name);
        paramsMap.put("telNumber",phone);
        paramsMap.put("detailInfo",detail);
        paramsMap.put("provinceName",provinceName);
        paramsMap.put("cityName",cityName);
        paramsMap.put("countyName",countyName);
        paramsMap.put("is_default",isDetail);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

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


    public void getAddressDetail(String id,final AddressDetailInterFace detailInterFace){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("id",id);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

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
