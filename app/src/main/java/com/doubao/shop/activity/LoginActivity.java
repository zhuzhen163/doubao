package com.doubao.shop.activity;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.entity.LoginBean;
import com.doubao.shop.presenter.LoginActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.CountDownButtonHelper;
import com.doubao.shop.tools.NetworkUtil;
import com.doubao.shop.tools.StringUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.LoginActivityView;

import org.json.JSONObject;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityView,View.OnClickListener{

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.btn_sendMessage)
    Button btn_sendMessage;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.et_message)
    EditText et_message;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;

    private String inputPhone;
    CountDownButtonHelper helper;

    @Override
    protected LoginActivityPresenter loadPresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        btn_sendMessage.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(LoginActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        setTitleName("登录");
        String agreement = "<font color='#999999'>" + "已阅读并同意" + "</font>" + "<font color='#FF7020'>" + "《平台服务协议》" + "</font>";
        tv_agreement.setText(Html.fromHtml(agreement));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.btn_sendMessage:
                inputPhone = et_phone.getText().toString();
                if (!TextUtils.isEmpty(inputPhone)){
                    if (AppUtils.isMobileNO(inputPhone)){
                        if (NetworkUtil.isNetworkConnected(mContext)){
                            helper = new CountDownButtonHelper(btn_sendMessage,"点击获取",60,1);
                            helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
                                @Override
                                public void finish() {
                                }
                            });
                            helper.start();
                            mPresenter.getSmsCodePresenter(inputPhone);
                        }
                        else {
                            ToastUtil.showLong(getString(R.string.network_error));
                        }
                    }else {
                        ToastUtil.showLong("请填写正确的手机号码");
                    }
                }
                else {
                    ToastUtil.showLong("手机号码不能为空");
                }
            break;
            case R.id.tv_login:
                String inputCode = et_message.getText().toString().trim();
                if (NetworkUtil.isNetworkConnected(mContext)){
                    if (StringUtils.isNotBlank(inputCode) && StringUtils.isNotBlank(inputPhone)){
                            mPresenter.getLoginPresenter(inputPhone,inputCode);
                    }else {
                        ToastUtil.showLong("请输入手机号或验证码");
                    }
                }else {
                    ToastUtil.showLong(getString(R.string.network_error));
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
    public void toLoginSuccess(String loginBean) {
        try {
            JSONObject jsonObject = new JSONObject(loginBean);
            if ("0".equals(jsonObject.getString("errno"))){
                LoginBean bean = AppUtils.parseJsonWithGson(loginBean, LoginBean.class);
                ConfigUtils.saveToken(bean.getData().getToken());
                ConfigUtils.saveUserId(bean.getData().getUserId());
                ConfigUtils.saveUserName(bean.getData().getUserInfo().getUsername());

                SwitchActivityManager.startMainActivity(mContext);
            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void toLoginFail(String message) {
        ToastUtil.showLong(message);
    }

    @Override
    public void getMessageSuccess(String message) {

    }

    @Override
    public void getMessageFail(String message) {
        if (helper!=null){
            helper.cancle(btn_sendMessage);
        }
        ToastUtil.showLong(message);
    }
}
