package com.qzxq.shop.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qzxq.shop.R;


/**
* @author zhuzhen
* create at 2018/11/26
* description: 删除地址弹窗
*/

public class DeleteAddressDialog extends Dialog {
    Context mContext;
    Button btn_cancel,btn_ok;
    DeleteDialog deleteDialog;

    public DeleteAddressDialog(Context context) {
        super(context, R.style.delete_address_dialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_address_dialog);
        initView();
    }

    public void initView() {
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.delete();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setDeleteDialog(DeleteDialog deleteDialog) {
        this.deleteDialog = deleteDialog;
    }

    public interface DeleteDialog {
        void delete();
    }
}
