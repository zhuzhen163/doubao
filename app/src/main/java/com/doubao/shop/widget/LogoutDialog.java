package com.doubao.shop.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doubao.shop.R;


/**
* @author zhuzhen
* create at 2018/12/7
* description: 退出登录dialog
*/

public class LogoutDialog extends Dialog {
    Button btn_cancel,btn_ok;
    LogoutDialogCallBack callBack;

    public LogoutDialog(Context context) {
        super(context, R.style.delete_address_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logount_dialog);
        initView();
    }

    public void initView() {
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.logout();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setCallBack(LogoutDialogCallBack callBack) {
        this.callBack = callBack;
    }

    public interface LogoutDialogCallBack {
        void logout();
    }
}
