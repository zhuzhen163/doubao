package com.qzxq.shop.presenter;


import com.qzxq.shop.base.BasePresenter;
import com.qzxq.shop.fragment.MineFragment;
import com.qzxq.shop.model.MineFragmentModel;

/**
 * Created by zhuzhen
 */

public class MineFragmentPresenter extends BasePresenter<MineFragmentModel,MineFragment> {


    @Override
    public MineFragmentModel loadModel() {
        return new MineFragmentModel();
    }
}
