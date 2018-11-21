package com.qzxq.shop.presenter;


import com.qzxq.shop.activity.FeedBackActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.model.FeedBackActivityModel;

/**
 * Created by zhuzhen
 */

public class FeedBackActivityPresenter extends BasePresenter<FeedBackActivityModel,FeedBackActivity> {


    @Override
    public FeedBackActivityModel loadModel() {
        return new FeedBackActivityModel();
    }
}
