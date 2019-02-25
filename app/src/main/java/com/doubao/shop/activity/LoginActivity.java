package com.doubao.shop.activity;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.entity.LoginBean;
import com.doubao.shop.http.Http;
import com.doubao.shop.http.UrlHelper;
import com.doubao.shop.presenter.LoginActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.ConfigUtils;
import com.doubao.shop.tools.CountDownButtonHelper;
import com.doubao.shop.tools.NetworkUtil;
import com.doubao.shop.tools.StringUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.LoginActivityView;
import com.doubao.shop.widget.AgreementClickableSpan;

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
    @BindView(R.id.cb_xieyi)
    CheckBox cb_xieyi;
    @BindView(R.id.ll_img_code)
    LinearLayout ll_img_code;
    @BindView(R.id.iv_code)
    ImageView iv_code;
    @BindView(R.id.et_imgCode)
    EditText et_imgCode;
    private boolean xieyi = false;

    private String inputPhone,imageCode = "";
    CountDownButtonHelper helper;

    @Override
    protected LoginActivityPresenter loadPresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    protected void initData() {
        inputPhone = ConfigUtils.getPhone();
        if (StringUtils.isNotBlank(inputPhone)){
            et_phone.setText(inputPhone);
            et_phone.setSelection(inputPhone.length());
        }
    }

    @Override
    protected void initListener() {
        btn_sendMessage.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        cb_xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    xieyi = true;
                }else {
                    xieyi = false;
                }
            }
        });
        iv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCodeImage();
            }
        });
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
        initLinkOnclick();
    }

    public void initLinkOnclick() {
        String a = "《斗宝俱乐部“克拉”使用规则》";
        String b = "《斗宝俱乐部用户注册协议》";
        String c = "《隐私权协议》";
        SpannableString spana = new SpannableString(a);
        SpannableString spanb = new SpannableString(b);
        SpannableString spanc = new SpannableString(c);
        ClickableSpan clicka = new AgreementClickableSpan(a, this);
        ClickableSpan clickb = new AgreementClickableSpan(b, this);
        ClickableSpan clickc = new AgreementClickableSpan(c, this);
        spana.setSpan(clicka, 0, a.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spanb.setSpan(clickb, 0, b.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spanc.setSpan(clickc, 0, c.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_agreement.setText("已阅读并同意");
        tv_agreement.append(spana);
        tv_agreement.append("、");
        tv_agreement.append(spanb);
        tv_agreement.append("、");
        tv_agreement.append(spanc);
        tv_agreement.setMovementMethod(LinkMovementMethod.getInstance());
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
                imageCode = et_imgCode.getText().toString();
                if (!TextUtils.isEmpty(inputPhone)){
                    if (AppUtils.isMobileNO(inputPhone)){
                        if (NetworkUtil.isNetworkConnected(mContext)){
                            mPresenter.getSmsCodePresenter(inputPhone,imageCode);
                        }else {
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
                        if (xieyi){
                            mPresenter.getLoginPresenter(inputPhone,inputCode,imageCode);
                        }else {
                            ToastUtil.showLong("请阅读并同意平台相关协议");
                        }
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
            if (jsonObject.has("errno")){
                if ("0".equals(jsonObject.getString("errno"))){
                    LoginBean bean = AppUtils.parseJsonWithGson(loginBean, LoginBean.class);
                    ConfigUtils.saveToken(bean.getData().getToken());
                    ConfigUtils.saveUserId(bean.getData().getUserId());
                    ConfigUtils.savePhone(inputPhone);
                    ConfigUtils.setAccountRefresh(true);
                    ConfigUtils.setCartRefresh(true);
                    SwitchActivityManager.exitActivity(LoginActivity.this);
                }else {
                    ToastUtil.showLong(jsonObject.getString("errmsg"));
                }
            }else {
                if (jsonObject.has("msg")){
                    ToastUtil.showLong(jsonObject.getString("msg"));
                }
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
        try {
            JSONObject jsonObject = new JSONObject(message);
            if ("0".equals(jsonObject.getString("errno"))){
                helper = new CountDownButtonHelper(btn_sendMessage,"点击获取",60,1);
                helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
                    @Override
                    public void finish() {
                    }
                });
                helper.start();
            }else if ("1".equals(jsonObject.getString("errno"))){
                if (jsonObject.has("count")){
                    String count = jsonObject.getString("count");
                    int countInt = Integer.parseInt(count);
                    if (countInt >= 5){
                        ll_img_code.setVisibility(View.VISIBLE);
                        showCodeImage();
                    }
                }
                ToastUtil.showLong(jsonObject.getString("msg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getMessageFail(String message) {
        if (helper!=null){
            helper.cancle(btn_sendMessage);
        }
        ToastUtil.showLong(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null){
            helper.timerCancel();
        }
    }

    public void showCodeImage() {
        Http.initP(LoginActivity.this).load(UrlHelper.BASE_URL + "image.jpg?date937").into(iv_code);
//        Glide.with(mContext).load(UrlHelper.BASE_URL + "image.jpg?date937")
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(iv_code);
    }
}
