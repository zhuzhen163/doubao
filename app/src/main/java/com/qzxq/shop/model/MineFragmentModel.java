package com.qzxq.shop.model;

import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

/**
 * Created by zhuzhen
 */

public class MineFragmentModel {

    public void getAccount(final AccountInterFace accountInterFace){

        Http.getHttpService(UrlHelper.BASE_URL).getAccount().
                compose(new StrTransformer<String>())
                .subscribe(new CommonSubscriber<String>(ZApplication.getAppContext()) {

                    @Override
                    public void onNext(String s) {
                        accountInterFace.getAccountSuccess(s);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        accountInterFace.getAccountFail(e.msg);
                    }
                });

    }

    public interface AccountInterFace{
        void getAccountSuccess(String s);
        void getAccountFail(String s);
    }
}
