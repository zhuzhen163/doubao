package com.doubao.shop.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.tools.CommonUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.LogUtil;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.widget.statusbar.StatusBarUtil;

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

        requestPermission();

        // 先显示默认图
        iv_pic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));
//         加载网络图片
//        Glide.with(this)
//                .load("")
//                .placeholder(R.drawable.img_transition_default)
//                .error(R.drawable.img_transition_default)
//                .into(iv_pic);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.i("sharedpreferences","show:"+ConfigUtils.getSaveShow()+",token:"+ConfigUtils.getToken());
                if (!ConfigUtils.getSaveShow()) {
                    SwitchActivityManager.startGuidePageActivity(TransitionActivity.this);
                }else {
                    toMainActivity();
                }
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

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(TransitionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TransitionActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }
}
