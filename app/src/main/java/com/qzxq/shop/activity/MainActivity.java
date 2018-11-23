package com.qzxq.shop.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.adapter.ViewPagerAdapter;
import com.qzxq.shop.application.ZApplication;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.fragment.MineFragment;
import com.qzxq.shop.fragment.ShopCartFragment;
import com.qzxq.shop.fragment.HomeFragment;
import com.qzxq.shop.fragment.ClassifyFragment;
import com.qzxq.shop.presenter.MainPresenter;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.MainModelView;
import com.qzxq.shop.widget.NoScrollViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.os.Process.killProcess;

public class MainActivity extends BaseActivity<MainPresenter> implements MainModelView {

    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.rb_first)
    RadioButton rb_first;
    @BindView(R.id.rb_two)
    RadioButton rb_two;
    @BindView(R.id.rb_three)
    RadioButton rb_three;
    @BindView(R.id.rb_four)
    TextView rb_four;
    private List<Fragment> viewpagerFragments;
    private HomeFragment homeFragment;
    private ClassifyFragment classifyFragment;
    private ShopCartFragment shopCartFragment;
    private MineFragment mineFragment;
    private ViewPagerAdapter adapter;
    private long mExitTime;
    private Dialog mDialog;

    @Override
    protected MainPresenter loadPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        rb_first.setOnClickListener(this);
        rb_two.setOnClickListener(this);
        rb_three.setOnClickListener(this);
        rb_four.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        setBaseTitleState(View.GONE);
        viewpagerFragments = new ArrayList<>();
        viewpager.setNoScroll(true);
        viewpager.setOffscreenPageLimit(3);
        homeFragment = new HomeFragment();
        classifyFragment = new ClassifyFragment();
        shopCartFragment = new ShopCartFragment();
        mineFragment = new MineFragment();
        viewpagerFragments.clear();
        viewpagerFragments.add(homeFragment);
        viewpagerFragments.add(classifyFragment);
        viewpagerFragments.add(shopCartFragment);
        viewpagerFragments.add(mineFragment);
        adapter = new ViewPagerAdapter(viewpagerFragments, getSupportFragmentManager());
        viewpager.setAdapter(adapter);


//        mPresenter.postPresener(1,10);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.rb_first:
                viewpager.setCurrentItem(0);
                break;
            case R.id.rb_two:
                viewpager.setCurrentItem(1);
                break;
            case R.id.rb_three:
                viewpager.setCurrentItem(2);
                break;
            case R.id.rb_four:
                viewpager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void showLoading() {
        setShowLoading(true);
    }
    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

    @Override
    public void showUpdate(final String version) {
        if (mDialog == null)
            mDialog = new AlertDialog.Builder(this)
                    .setTitle("检测到有新版本")
                    .setMessage("当前版本:"+version)
                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.downApk(MainActivity.this);
                        }
                    })
                    .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create();

        //重写这俩个方法，一般是强制更新不能取消弹窗
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK && mDialog != null && mDialog.isShowing();
            }
        });

        mDialog.show();
    }

    @Override
    public void showProgress(int progress) {

    }

    @Override
    public void showFail(String msg) {
        ToastUtil.showLong(msg);
    }

    @Override
    public void showComplete(File file) {
        try {
            String authority = getApplicationContext().getPackageName() + ".fileProvider";
            Uri fileUri = FileProvider.getUriForFile(this, authority, file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //7.0以上需要添加临时读取权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            } else {
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }

            startActivity(intent);

            //弹出安装窗口把原程序关闭。
            //避免安装完毕点击打开时没反应
            killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 双击退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showLong("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.finish();
                ZApplication.getAppContext().exitApp();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
