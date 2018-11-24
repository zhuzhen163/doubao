package com.qzxq.shop.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.entity.AddressDetailBean;
import com.qzxq.shop.entity.CreateAddressBean;
import com.qzxq.shop.presenter.CreateAddressActivityPresenter;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.tools.StringUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.tools.ToastUtil;
import com.qzxq.shop.view.CreateAddressActivityView;
import com.qzxq.shop.widget.addresspicker.AddressPickerView;

import org.json.JSONObject;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/11/20
* description:新建地址
*/
public class CreateAddressActivity extends BaseActivity<CreateAddressActivityPresenter> implements CreateAddressActivityView, View.OnClickListener{

    private PopupWindow popupWindow;
    @BindView(R.id.tv_selectAddress)
    TextView tv_selectAddress;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_detail)
    EditText et_detail;
    @BindView(R.id.cb_isDetail)
    CheckBox cb_isDetail;
    @BindView(R.id.tv_cancelDetail)
    TextView tv_cancelDetail;
    @BindView(R.id.tv_saveAddress)
    TextView tv_saveAddress;
    private String isDeatil = "0",id = "";//记录是否默认地址

    @Override
    protected CreateAddressActivityPresenter loadPresenter() {
        return new CreateAddressActivityPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getAddressDetail();
    }

    @Override
    protected void initListener() {
        tv_saveAddress.setOnClickListener(this);
        tv_cancelDetail.setOnClickListener(this);
        tv_selectAddress.setOnClickListener(this);
        cb_isDetail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isDeatil = "1";
                }else {
                    isDeatil = "0";
                }
            }
        });
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else {
                    SwitchActivityManager.exitActivity(CreateAddressActivity.this);
                }
            }
        });
    }

    @Override
    protected void initView() {
        setTitleName("新建地址");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_address;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.tv_selectAddress:
                AppUtils.hideInputMethod(CreateAddressActivity.this);
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        showAddressPickerPop();
                    }
                }, 100);
                break;
            case R.id.tv_saveAddress:
                String name = et_name.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String selectAddress = tv_selectAddress.getText().toString();
                String detail = et_detail.getText().toString().trim();
                if (StringUtils.isNotBlank(name)){
                    if (StringUtils.isNotBlank(phone) && AppUtils.isMobileNO(phone)){
                        if (StringUtils.isNotBlank(selectAddress)){
                            if (StringUtils.isNotBlank(detail)){
                                mPresenter.getSaveDetail(id,name,phone,detail,isDeatil);
                            }else {
                                ToastUtil.showLong("请输入详细地址");
                            }
                        }else {
                            ToastUtil.showLong("请选择地址");
                        }
                    }else {
                        ToastUtil.showLong("请输入正确的手机号");
                    }
                }else {
                    ToastUtil.showLong("姓名不能为空");
                }
                break;
            case R.id.tv_cancelDetail:
                SwitchActivityManager.exitActivity(CreateAddressActivity.this);
                break;
        }
    }

    /**
     * 显示地址选择的pop
     */
    private void showAddressPickerPop() {
        popupWindow = new PopupWindow(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
        AddressPickerView addressView = (AddressPickerView) rootView.findViewById(R.id.apvAddress);
        TextView tv_cancel = (TextView) rootView.findViewById(R.id.tv_cancel);
        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                tv_selectAddress.setText(address);
                popupWindow.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(rootView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.address_picker_anim);
        popupWindow.showAsDropDown(tv_selectAddress);

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popupWindow != null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }else {
                SwitchActivityManager.exitActivity(CreateAddressActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void saveDetailSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                SwitchActivityManager.exitActivity(CreateAddressActivity.this);
            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveDetailFail(String s) {
        ToastUtil.showLong(s);
    }

    @Override
    public void getDetailSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                if (jsonObject.has("data")){
                    CreateAddressBean bean = AppUtils.parseJsonWithGson(s, CreateAddressBean.class);
                    AddressDetailBean detailBean = bean.getData();
                    et_name.setText(detailBean.getUserName());
                    et_phone.setText(detailBean.getTelNumber());
                    tv_selectAddress.setText(detailBean.getFull_region());
                    et_detail.setText(detailBean.getDetailInfo());
                    if ("1".equals(detailBean.getIsDefault())){
                        cb_isDetail.setChecked(true);
                        isDeatil = "1";
                    }
                    id = detailBean.getId();
                }

            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getDetailFail(String s) {
        ToastUtil.showLong(s);
    }
}
