package com.doubao.shop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doubao.shop.R;


/**
 * zhuzhen
 * 公共的头部
 */

public class PublicTitleView extends RelativeLayout {
    private View mView;
    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_icon;
    private TextView tv_function;
    public PublicTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PublicTitleView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_public_title,this);
        initView();
    }
    public void  initView(){
        iv_back = (ImageView) mView.findViewById(R.id.iv_back);
        tv_title = (TextView) mView.findViewById(R.id.tv_title);
        iv_icon = (ImageView) mView.findViewById(R.id.iv_icon);
        tv_function = (TextView) mView.findViewById(R.id.tv_function);
    }
    public void setBackListener(OnClickListener clickListener){
        iv_back.setOnClickListener(clickListener);
    }
    public void setBackState(int state){
        iv_back.setVisibility(state);
    }
    public void setTitleName(String name){
        tv_title.setText(name);
    }
    public void setRightIcon(int state){
        iv_icon.setVisibility(state);
    }

    public void setRightState(boolean state ){
        if (state){
            tv_function.setVisibility(View.VISIBLE);
        }else {
            tv_function.setVisibility(View.INVISIBLE);
        }
    }

    public void setRightText(String text){
        tv_function.setText(text);
    }
    public void setRightListener(OnClickListener clickListener){
        tv_function.setOnClickListener(clickListener);
    }
}
