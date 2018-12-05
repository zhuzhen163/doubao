package com.qzxq.shop.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qzxq.shop.R;
import com.qzxq.shop.tools.CommonUtils;
import com.qzxq.shop.tools.ConstantsImageUrl;
import com.qzxq.shop.widget.statusbar.StatusBarUtil;

import java.util.Random;

public class TransitionActivity extends FragmentActivity{

    private ImageView iv_pic;
    private TextView tv_jump;
    private boolean isIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_transition);
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme),0);

        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        tv_jump = (TextView) findViewById(R.id.tv_jump);

        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        // 先显示默认图
        iv_pic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(iv_pic);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);

        tv_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainActivity();
            }
        });
    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }
}
