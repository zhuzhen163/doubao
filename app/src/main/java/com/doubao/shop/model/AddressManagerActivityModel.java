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

public class AddressManagerActivityModel {

    public void deleteAddress(String id, final DeleteAddressInterFace deleteAddressInterFace){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("id",id);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

        Http.getHttpService(UrlHelper.BASE_URL).deleteAddress(body).
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
