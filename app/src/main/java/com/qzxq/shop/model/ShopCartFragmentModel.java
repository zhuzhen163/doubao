package com.qzxq.shop.model;

import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

import okhttp3.RequestBody;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class ShopCartFragmentModel {

    public void update(RequestBody body, final UpdateCallBack updateCallBack){

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

    public void isCheck(RequestBody body, final IsCheckCallBack checkCallBack){

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

    public void cartDelete(RequestBody body, final DeleteCallBack deleteCallBack){

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
