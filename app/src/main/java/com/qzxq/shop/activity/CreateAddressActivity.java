package com.qzxq.shop.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.presenter.CreateAddressActivityPresenter;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.widget.addresspicker.AddressPickerView;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/11/20
* description:新建地址
*/
public class CreateAddressActivity extends BaseActivity<CreateAddressActivityPresenter> implements View.OnClickListener{

    private PopupWindow popupWindow;
    @BindView(R.id.tv_selectAddress)
    TextView tv_selectAddress;

    @Override
    protected CreateAddressActivityPresenter loadPresenter() {
        return new CreateAddressActivityPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_selectAddress.setOnClickListener(this);
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
    }

    @Override
    public void hideLoading() {

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
}
