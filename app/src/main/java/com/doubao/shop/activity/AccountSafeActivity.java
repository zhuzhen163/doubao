package com.doubao.shop.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.entity.UserInfo;
import com.doubao.shop.presenter.AccountSafeActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.NetworkUtil;
import com.doubao.shop.tools.StringUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.AccountSafeActivityView;
import com.doubao.shop.widget.LogoutDialog;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
* @author zhuzhen
* create at 2018/12/7
* description: 账户安全
*/
public class AccountSafeActivity extends BaseActivity <AccountSafeActivityPresenter> implements AccountSafeActivityView,LogoutDialog.LogoutDialogCallBack{

    @BindView(R.id.tv_logout)
    TextView tv_logout;
    @BindView(R.id.tv_authIdNo)
    TextView tv_authIdNo;
    @BindView(R.id.tv_authName)
    TextView tv_authName;
    @BindView(R.id.civ_headImage)
    CircleImageView civ_headImage;
    @BindView(R.id.tv_bindNum)
    TextView tv_bindNum;

    private LogoutDialog dialog;

    @Override
    protected AccountSafeActivityPresenter loadPresenter() {
        return new AccountSafeActivityPresenter();
    }

    @Override
    protected void initData() {}

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtil.isNetworkConnected(mContext)){
            mPresenter.userInfo();
        }else {
            ToastUtil.showLong(getString(R.string.network_error));
        }
    }

    @Override
    protected void initListener() {
        tv_logout.setOnClickListener(this);
        tv_authName.setOnClickListener(this);
        tv_authIdNo.setOnClickListener(this);
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(AccountSafeActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        setTitleName("账号绑定");
        dialog = new LogoutDialog(this);
        dialog.setCallBack(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.tv_authName:
                SwitchActivityManager.startRealNameActivity(AccountSafeActivity.this);
                break;
            case R.id.tv_authIdNo:
                SwitchActivityManager.startRealNameActivity(AccountSafeActivity.this);
                break;
            case R.id.tv_logout:
                if (dialog != null){
                    dialog.show();
                }
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
    public void getAccountSuccess(String s) {
        try {
//            JSONObject jsonObject = new JSONObject(s);
//            if ("0".equals(jsonObject.getString("errno"))){
//                UserInfo bean = AppUtils.parseJsonWithGson(s, UserInfo.class);
//                AppUtils.setImage(AccountSafeActivity.this,bean.getAvatar(),civ_headImage);
//                if (bean.getUsername() != null){
//                    tv_authName.setText("已认证");
//                    tv_authName.setEnabled(false);
//                }
//                if (bean.getIdcard() != null){
//                    tv_authIdNo.setText("已认证");
//                    tv_authIdNo.setEnabled(false);
//                }
//
//            }else {
//                ToastUtil.showLong(jsonObject.getString("errmsg"));
//            }
            UserInfo bean = AppUtils.parseJsonWithGson(s, UserInfo.class);
            if (bean != null){
                UserInfo.User data = bean.getData();
                tv_bindNum.setText(data.getMobile());
//                AppUtils.setImage(AccountSafeActivity.this,data.getAvatar(),civ_headImage);
                if (data.getUsername() != null && StringUtils.isNotBlank(data.getUsername())){
                    tv_authName.setText(AppUtils.nameEncrypt(data.getUsername()));
                    tv_authName.setEnabled(false);
                }
                if (data.getIdcard() != null){
                    tv_authIdNo.setText(AppUtils.idEncrypt(data.getIdcard()));
                    tv_authIdNo.setEnabled(false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAccountFail(String s) {
        ToastUtil.showLong(s);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SwitchActivityManager.exitActivity(AccountSafeActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void logout() {
        ConfigUtils.cleatSP();
        ConfigUtils.setAccountRefresh(true);
        SwitchActivityManager.startMainActivity(AccountSafeActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null){
            dialog.dismiss();
        }
    }
}
