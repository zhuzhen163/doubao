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
    @POST("address/save")
    Observable<String> getSaveDetail(@Field("id") String id,@Field("userName") String userName,@Field("telNumber") String telNumber,
                                     @Field("detailInfo") String detailInfo,@Field("is_default") String is_default);

    @POST("address/detail")
    Observable<String> getAddressDetail();

    @POST("address/list")
    Observable<String> getAddressList();

    @POST("user/userAccount")
    Observable<String> getAccount();

    @POST("cart/index")
    Observable<String> getShopCartList();

    @FormUrlEncoded()
    @POST("auth/login_by_mobile")
    Observable<String> toLogin(@Field("mobile") String mobile,@Field("code") String  code);

    @FormUrlEncoded()
    @POST("sendSms")
    Observable<String> getSMSCode(@Field("mobile") String mobile);

}
