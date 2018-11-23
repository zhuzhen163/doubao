package com.qzxq.shop.transformer;


import com.qzxq.shop.exception.ErrorType;
import com.qzxq.shop.exception.ExceptionEngine;
import com.qzxq.shop.exception.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 *zhuzhen
 * 转换器
 */

public class StrErrorTransformer<T> implements Observable.Transformer<String, T>  {

    private static StrErrorTransformer rorTransformer = null;
    @Override
    public Observable<T> call(Observable<String> stringObservable) {
        return stringObservable.map(new Func1<String, T>() {
            @Override
            public T call(String s) {
                if (s == null || s.equals(""))
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");
                return (T) s;
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
    public static <T> StrErrorTransformer<T> getInstance() {
        if (rorTransformer == null) {
            synchronized (StrErrorTransformer.class) {
                if (rorTransformer == null) {
                    rorTransformer = new StrErrorTransformer<>();
                }
            }
        }
        return rorTransformer;
    }
}
