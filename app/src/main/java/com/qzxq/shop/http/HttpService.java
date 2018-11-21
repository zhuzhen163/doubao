package com.qzxq.shop.http;


import com.qzxq.shop.base.BaseHttpResult;
import com.qzxq.shop.entity.GankIoDataBean;
import com.qzxq.shop.entity.RaidersDataEntity;
import com.qzxq.shop.entity.VersionEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuzhen on 2017/11/2.
 * 接口统一处理
 */

public interface HttpService {

    /**
     * 测试速贷之家升级 get请求
     * 测试框架接口是否走通
     * @param versionId
     * @param appType
     * @param versionName
     * @param platType
     * @return
     */
    @GET("?r=api4/version/version")
    Observable<BaseHttpResult<VersionEntity>> checkVersition(@Query("versionId") String versionId, @Query("appType") String appType, @Query("versionName") String versionName, @Query("platType") String platType);
    @FormUrlEncoded()
    @POST("?r=api4/product/guide")
    Observable<BaseHttpResult<RaidersDataEntity>> postDemo(@Field("page") int page, @Field("num") int num);

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    Observable<GankIoDataBean> getCustom(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);


}
