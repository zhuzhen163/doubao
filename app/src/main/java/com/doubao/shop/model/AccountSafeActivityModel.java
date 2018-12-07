package com.doubao.shop.model;


import com.doubao.shop.application.ZApplication;
import com.doubao.shop.exception.ApiException;
import com.doubao.shop.http.Http;
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.subscriber.CommonSubscriber;
import com.doubao.shop.transformer.StrTransformer;

public class AccountSafeActivityModel {


    public void userInfo(final AccountInterFace interFace){

        Http.getHttpService(UrlHelper.BASE_URL).userInfo().
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        interFace.getAccountSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        interFace.getAccountFail(e.msg);
                    }
                });

    }

    public interface AccountInterFace{
        void getAccountSuccess(String s);
        void getAccountFail(String s);
    }
}
