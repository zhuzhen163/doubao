package com.qzxq.shop.transformer;


import com.qzxq.shop.base.BaseHttpResult;
import com.qzxq.shop.exception.ErrorType;
import com.qzxq.shop.exception.ExceptionEngine;
import com.qzxq.shop.exception.ServerException;
import com.qzxq.shop.tools.LogUtil;

import rx.Observable;
import rx.functions.Func1;

/**
 *zhuzhen
 * 转换器
 * 标准数据格式
 * {code：200，message："失败或者错误"，data：{具体数据}}
 */

public class ErrorTransformer<T> implements Observable.Transformer<BaseHttpResult<T>, T>  {

    private static ErrorTransformer errorTransformer = null;
    private static final String TAG = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<BaseHttpResult<T>> responseObservable) {
        return responseObservable.map(new Func1<BaseHttpResult<T>, T>() {
            @Override
            public T call(BaseHttpResult<T> httpResult) {
                if (httpResult == null)
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");
                    LogUtil.i(TAG, httpResult.toString());
                if (httpResult.getCode() != ErrorType.SUCCESS)
                    throw new ServerException(httpResult.getCode(), httpResult.getMessage());
                    LogUtil.i(TAG,  httpResult.getMessage());
                return httpResult.getData();
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                //ExceptionEngine为处理异常的驱动器
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });
    }

    /**
     * @return 线程安全, 双层校验
     */
    public static <T> ErrorTransformer<T> getInstance() {
        if (errorTransformer == null) {
            synchronized (ErrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformer<>();
                }
            }
        }
        return errorTransformer;
    }
}
