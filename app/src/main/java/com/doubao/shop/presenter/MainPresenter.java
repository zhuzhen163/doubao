package com.doubao.shop.presenter;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import com.doubao.shop.activity.MainActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.model.MainModel;
import com.doubao.shop.service.DownloadService;

import java.io.File;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class MainPresenter extends BasePresenter<MainModel, MainActivity> {

    private ServiceConnection conn;


    public void downApk(Context context) {
        final String url = "http://download.qzxq.com/xsjq/apk_xianshangjieqian-4-1.0.3.apk";
        if (conn == null)
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                    DownloadService myService = binder.getService();
                    myService.downApk(url, new DownloadService.DownloadCallback() {
                        @Override
                        public void onPrepare() {

                        }

                        @Override
                        public void onProgress(int progress) {
                            getView().showProgress(progress);
                        }

                        @Override
                        public void onComplete(final File file) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showComplete(file);
                                }
                            },100);
                        }

                        @Override
                        public void onFail(String msg) {
                            getView().showFail(msg);
                        }
                    });
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    //意味中断，较小发生，酌情处理
                }
            };
        Intent intent = new Intent(context,DownloadService.class);
        context.bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    @Override
    public MainModel loadModel() {
        return new MainModel();
    }
}
