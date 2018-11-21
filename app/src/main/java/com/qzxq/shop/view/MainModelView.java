package com.qzxq.shop.view;


import com.qzxq.shop.base.BaseView;

import java.io.File;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface MainModelView extends BaseView {
    //更新升级
    void showUpdate(String version);
    void showProgress(int progress);
    void showFail(String msg);
    void showComplete(File file);
}
