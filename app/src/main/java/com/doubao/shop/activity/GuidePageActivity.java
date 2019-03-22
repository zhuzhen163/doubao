package com.doubao.shop.activity;

import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.doubao.shop.R;
import com.doubao.shop.adapter.GuideViewPagerAdapter;
import com.doubao.shop.application.ZApplication;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.base.BasePresenter;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.SwitchActivityManager;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2019/1/17
* description:
*/
public class GuidePageActivity extends BaseActivity {
    GuideViewPagerAdapter adapter;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.join)
    TextView join;
    public long mExitTime = 0;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivityManager.exitActivity(GuidePageActivity.this);
                SwitchActivityManager.startMainActivity(mContext);
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position==2){
                    join.setVisibility(View.VISIBLE);
                }
                else {
                    join.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                ZApplication.getAppContext().exitApp();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void initView() {
        setBaseTitleState(View.GONE);
        ConfigUtils.saveGuide(true);
        int[] listImage = {R.drawable.guide_page_1, R.drawable.guide_page_2, R.drawable.guide_page_3};
        adapter = new GuideViewPagerAdapter(mContext, listImage);
        viewpager.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
