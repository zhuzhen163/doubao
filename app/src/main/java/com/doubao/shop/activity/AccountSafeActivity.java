package com.doubao.shop.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.entity.UserInfo;
import com.doubao.shop.presenter.AccountSafeActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.NetworkUtil;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.AccountSafeActivityView;

import org.json.JSONObject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
* @author zhuzhen
* create at 2018/12/7
* description: 账户安全
*/
public class AccountSafeActivity extends BaseActivity <AccountSafeActivityPresenter> implements AccountSafeActivityView{

    @BindView(R.id.tv_logout)
    TextView tv_logout;
    @BindView(R.id.tv_bindPhone)
    TextView tv_bindPhone;
    @BindView(R.id.tv_authIdNo)
    TextView tv_authIdNo;
    @BindView(R.id.tv_authName)
    TextView tv_authName;
    @BindView(R.id.civ_headImage)
    CircleImageView civ_headImage;

    @Override
    protected AccountSafeActivityPresenter loadPresenter() {
        return new AccountSafeActivityPresenter();
    }

    @Override
    protected void initData() {
        if (NetworkUtil.isNetworkConnected(mContext)){
            mPresenter.userInfo();
        }else {
            ToastUtil.showLong(getString(R.string.network_error));
        }
    }

    @Override
    protected void initListener() {

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected void otherViewClick(View view) {

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
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                UserInfo bean = AppUtils.parseJsonWithGson(s, UserInfo.class);
                AppUtils.setImage(AccountSafeActivity.this,bean.getAvatar(),civ_headImage);
            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
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
}
