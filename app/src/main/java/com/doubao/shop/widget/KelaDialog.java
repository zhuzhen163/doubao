package com.doubao.shop.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doubao.shop.R;

/**
* @author zhuzhen
* create at 2018/12/24
* description:
*/
public class KelaDialog extends Dialog {
    Context mContext;
    Button btn_know;

    public KelaDialog(Context context) {
        super(context, R.style.delete_address_dialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kela_dialog);
        initView();
    }

    public void initView() {
        btn_know = (Button) findViewById(R.id.btn_know);
        btn_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
