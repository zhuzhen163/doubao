package com.qzxq.shop.model;


import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

import okhttp3.RequestBody;

public class FeedBackActivityModel {

    public void saveFeedBack(RequestBody body, final SaveFeedBackInterFace interFace){

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
