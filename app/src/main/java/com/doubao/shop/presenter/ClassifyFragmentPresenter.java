package com.doubao.shop.presenter;


import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.fragment.ClassifyFragment;
import com.doubao.shop.model.ClassifyFragmentModel;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class ClassifyFragmentPresenter extends BasePresenter<ClassifyFragmentModel,ClassifyFragment> {


    @Override
    public ClassifyFragmentModel loadModel() {
        return new ClassifyFragmentModel();
    }
}
