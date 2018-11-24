package com.qzxq.shop.model;

import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.exception.ApiException;
import com.qzxq.shop.http.Http;
import com.qzxq.shop.http.UrlHelper;
import com.qzxq.shop.subscriber.CommonSubscriber;
import com.qzxq.shop.transformer.StrTransformer;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class ShopCartFragmentModel {

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
