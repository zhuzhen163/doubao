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

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class ShopCartFragmentModel {

    public void update(String goodsId,String id,String productId,int number, final UpdateCallBack updateCallBack){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("goodsId",goodsId);
        paramsMap.put("id",id);
        paramsMap.put("productId",productId);
        paramsMap.put("number",Integer.toString(number));
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

        Http.getHttpService(UrlHelper.BASE_URL).update(body).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        updateCallBack.updateSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        updateCallBack.updateFail(e.msg);
                    }
                });

    }

    public interface UpdateCallBack{
        void updateSuccess(String s);
        void updateFail(String s);
    }

    public void isCheck(String isChecked,String checkShop, final IsCheckCallBack checkCallBack){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("isChecked",isChecked);
        paramsMap.put("productIds",checkShop);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

        Http.getHttpService(UrlHelper.BASE_URL).isCheck(body).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        checkCallBack.isCheckSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        checkCallBack.isCheckFail(e.msg);
                    }
                });

    }

    public interface IsCheckCallBack{
        void isCheckSuccess(String s);
        void isCheckFail(String s);
    }

    public void cartDelete(String deleteIds, final DeleteCallBack deleteCallBack){

        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("productIds",deleteIds);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

        Http.getHttpService(UrlHelper.BASE_URL).cartDelete(body).
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        deleteCallBack.deleteSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        deleteCallBack.deleteFail(e.msg);
                    }
                });

    }

    public interface DeleteCallBack{
        void deleteSuccess(String s);
        void deleteFail(String s);
    }

    public void getShopCartList(final ShopCartList shopCartList){

        Http.getHttpService(UrlHelper.BASE_URL).getShopCartList().
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        shopCartList.getListSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        shopCartList.getListFail(e.msg);
                    }
                });

    }

    public interface ShopCartList{
        void getListSuccess(String s);
        void getListFail(String s);
    }
}
