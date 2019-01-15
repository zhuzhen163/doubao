package com.doubao.shop.http;


import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhuzhen
 * 接口统一处理
 */

public interface HttpService {

    @POST("order/submit")
    Observable<String> orderSubmit(@Body RequestBody body);

    @POST("user/bind_user_idcard")
    Observable<String> bindUser(@Body RequestBody body);

    @POST("user/userInfo")
    Observable<String> userInfo();

    @POST("cart/checkout")
    Observable<String> checkCart(@Body RequestBody body);

    @POST("address/delete")
    Observable<String> deleteAddress(@Body RequestBody body);

    @POST("feedback/save")
    Observable<String> saveFeedBack(@Body RequestBody body);

    @POST("address/save")
    Observable<String> getSaveDetail(@Body RequestBody body);

    @POST("address/detail")
    Observable<String> getAddressDetail(@Body RequestBody body);

    @FormUrlEncoded()
    @POST("region/list")
    Observable<String> getRegionList(@Field("parentId") String parentId);

    @POST("address/list")
    Observable<String> getAddressList();

    @POST("user/userAccount")
    Observable<String> getAccount();

    @POST("cart/delete")
    Observable<String> cartDelete(@Body RequestBody body);

    @POST("cart/update")
    Observable<String> update(@Body RequestBody body);

    @POST("cart/checked")
    Observable<String> isCheck(@Body RequestBody body);

    @POST("cart/index")
    Observable<String> getShopCartList();

    @FormUrlEncoded()
    @POST("auth/login_by_mobile")
    Observable<String> toLogin(@Field("mobile") String mobile,@Field("code") String  code);

    @FormUrlEncoded()
    @POST("sendSms")
    Observable<String> getSMSCode(@Field("mobile") String mobile,@Field("imageCode") String imageCode);

}
