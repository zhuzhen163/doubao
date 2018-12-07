package com.doubao.shop.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.presenter.RealNameActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.StringUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.RealNameActivityView;

import org.json.JSONObject;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/12/7
* description: 实名认证
*/
public class RealNameActivity extends BaseActivity <RealNameActivityPresenter>implements RealNameActivityView {

    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_idCard)
    EditText et_idCard;

    @Override
    protected RealNameActivityPresenter loadPresenter() {
        return new RealNameActivityPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_submit.setOnClickListener(this);
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(RealNameActivity.this);
            }
        });
    }

    @Override
    protected void initView() {
        setTitleName("实名认证");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.tv_submit:
                String name = et_name.getText().toString().trim();
                if (StringUtils.isNotBlank(name)){
                    String idCard = et_idCard.getText().toString().trim();
                    if (AppUtils.personIdValidation(idCard)){
                        mPresenter.bindUser(name,idCard);
                    }else {
                        ToastUtil.showLong("请输入正确的身份证号");
                    }
                }else {
                    ToastUtil.showLong("姓名不能为空");
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
    public void realNameSuccess(String s) {
        try {
            JSONObject object = new JSONObject(s);
            if ("200".equals(object.getString("code"))){
                ToastUtil.showLong(object.getString("msg"));
                SwitchActivityManager.exitActivity(RealNameActivity.this);
            }else {
                ToastUtil.showLong(object.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void realNameFail(String s) {
        ToastUtil.showLong(s);
    }
}
