package com.qzxq.shop.presenter;


import com.qzxq.shop.activity.FeedBackActivity;
import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.model.FeedBackActivityModel;

/**
 * Created by zhuzhen
 */

public class FeedBackActivityPresenter extends BasePresenter<FeedBackActivityModel,FeedBackActivity> {

    public void saveFeedBack(String phone,String index,String content) {
        if (getView()!=null){
            getView().showLoading();
        }

        getModel().saveFeedBack(phone,index,content, new FeedBackActivityModel.SaveFeedBackInterFace() {
            @Override
            public void saveSuccess(String s) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().saveSuccess(s);
                }
            }

            @Override
            public void saveFail(String s) {
                if (getView()!=null){
                    getView().hideLoading();
                    getView().saveFail(s);
                }
            }
        });
    }
    @Override
    public FeedBackActivityModel loadModel() {
        return new FeedBackActivityModel();
    }
}
