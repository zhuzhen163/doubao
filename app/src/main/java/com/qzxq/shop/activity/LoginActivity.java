package com.qzxq.shop.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.entity.LoginBean;
import com.qzxq.shop.presenter.LoginActivityPresenter;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.tools.ConfigUtils;
import com.qzxq.shop.tools.CountDownButtonHelper;
import com.qzxq.shop.tools.NetworkUtil;
import com.qzxq.shop.tools.StringUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.LoginActivityView;

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

    private String inputPhone;
    CountDownButtonHelper helper;

    @Override
    protected LoginActivityPresenter loadPresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    protected void initData() {
        setTitleName("登录");
    }

    @Override
    protected void initListener() {
        btn_sendMessage.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initView() {
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
                            ToastUtil.showLong("请检查网络");
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
                    ToastUtil.showLong("请检查网络");
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
    }
}
