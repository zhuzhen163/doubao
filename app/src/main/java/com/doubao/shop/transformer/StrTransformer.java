package com.doubao.shop.transformer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 奇子投资
 * Created by 吕彦波
 * 2017/3/13
 * 后台返回的数据是json 不是标准的code message data 所有自定义json解析
 */

public class StrTransformer<T> implements Observable.Transformer<String,T>{

    @Override
    public Observable<T> call(Observable<String> stringObservable) {
        return stringObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(StrErrorTransformer.<T>getInstance());
    }
}
