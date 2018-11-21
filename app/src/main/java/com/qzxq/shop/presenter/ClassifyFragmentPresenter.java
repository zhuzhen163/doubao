package com.qzxq.shop.presenter;


import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.fragment.ClassifyFragment;
import com.qzxq.shop.model.ClassifyFragmentModel;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class ClassifyFragmentPresenter extends BasePresenter<ClassifyFragmentModel,ClassifyFragment> {


    @Override
    public ClassifyFragmentModel loadModel() {
        return new ClassifyFragmentModel();
    }
}
