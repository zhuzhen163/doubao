package com.qzxq.shop.model;


import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

public class FeedBackActivityModel {

    public void saveFeedBack(String mobile,String index,String content,final SaveFeedBackInterFace interFace){

        Http.getHttpService(UrlHelper.BASE_URL).saveFeedBack(mobile,index,content).
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
