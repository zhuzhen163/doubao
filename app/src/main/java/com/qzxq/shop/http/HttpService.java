package com.qzxq.shop.http;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhuzhen
 * 接口统一处理
 */

public interface HttpService {

    @FormUrlEncoded()
    @POST("auth/login_by_mobile")
    Observable<String>  toLogin(@Field("mobile") String mobile,@Field("code") String  code);

    @FormUrlEncoded()
    @POST("sendSms")
    Observable<String>  getSMSCode(@Field("mobile") String mobile);

}
