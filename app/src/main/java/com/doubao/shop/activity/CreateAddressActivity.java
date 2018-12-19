package com.doubao.shop.activity;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.BaseActivity;
import com.doubao.shop.entity.AddressDetailBean;
import com.doubao.shop.entity.CreateAddressBean;
import com.doubao.shop.presenter.CreateAddressActivityPresenter;
import com.doubao.shop.tools.AppUtils;
import com.doubao.shop.tools.StringUtils;
import com.doubao.shop.tools.SwitchActivityManager;
import com.doubao.shop.tools.ToastUtil;
import com.doubao.shop.view.CreateAddressActivityView;
import com.doubao.shop.widget.addressselector.AddressBean;
import com.doubao.shop.widget.addressselector.BottomDialog;
import com.doubao.shop.widget.addressselector.DataProvider;
import com.doubao.shop.widget.addressselector.SelectedListener;
import com.doubao.shop.widget.addressselector.Selector;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private String isDetail = "0",id = "0",addressName = "",code = "";
    private List<AddressBean.ChangeRecordsBean>  recordsBeans;
    DataProvider.DataReceiver receiver1;
    private BottomDialog dialog;

    @Override
    protected CreateAddressActivityPresenter loadPresenter() {
        return new CreateAddressActivityPresenter();
    }

    @Override
    protected void initData() {

        AddressDetailBean bean = (AddressDetailBean)getIntent().getSerializableExtra("bean");
        if (bean != null){
            id = bean.getId();
            et_name.setText(bean.getUserName());
            et_phone.setText(bean.getTelNumber());
            tv_selectAddress.setText(bean.getFull_region());
            et_detail.setText(bean.getDetailInfo());
        }
        mPresenter.getAddressDetail(id);
        mPresenter.getRegionList("0");
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
                    isDetail = "1";
                }else {
                    isDetail = "0";
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
                        if (dialog == null){
                            showDialog();
                        }else {
                            dialog.show();
                        }
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
                            String[] address = selectAddress.split(" ");
                            String[] code = this.code.split(" ");
                            if (StringUtils.isNotBlank(detail)){

                                mPresenter.getSaveDetail(id,name,phone,detail,address[0],code[0],address[1],code[1],address[2],code[2],isDetail);

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

    private void showDialog() {
        Selector selector = new Selector(this, 3);
        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, DataReceiver receiver) {
                receiver1 = receiver;
                //根据tab的深度和前一项选择的id，获取下一级菜单项
                Log.i("provideData", "provideData: currentDeep >>> "+currentDeep+" preId >>> "+preId);
                if (preId == 0){
                    receiver.send(recordsBeans);
                }else {
                    mPresenter.getRegionList(Integer.toString(preId));
                }
            }
        });
        selector.setSelectedListener(new SelectedListener() {
            @Override
            public void onAddressSelected(ArrayList<AddressBean.ChangeRecordsBean> selectAbles) {
                addressName = "";
                code = "";
                for (AddressBean.ChangeRecordsBean selectAble : selectAbles) {
                    addressName += selectAble.getName()+" ";
                    code += selectAble.getThird_code()+" ";
                }
//                for (int i = 0; i < selectAbles.size(); i++) {
//                    AddressBean.ChangeRecordsBean changeRecordsBean = selectAbles.get(i);
//                    if (changeRecordsBean != null){
//                        result += changeRecordsBean.getName()+" ";
//                    }
//                }
                tv_selectAddress.setText(addressName);
                if (dialog != null){
                    dialog.dismiss();
                }
            }

            @Override
            public void dialogCancel() {
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });

        dialog = new BottomDialog(this);
        dialog.init(this,selector);
        dialog.show();
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
                        isDetail = "1";
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

    @Override
    public void getRegionSuccess(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if ("0".equals(jsonObject.getString("errno"))){
                AddressBean bean = AppUtils.parseJsonWithGson(s, AddressBean.class);
                recordsBeans = bean.getData();
                if (receiver1 != null){
                    receiver1.send(recordsBeans);
                }
            }else {
                ToastUtil.showLong(jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getRegionFail(String s) {
        ToastUtil.showShort(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null){
            dialog.cancel();
            dialog = null;
        }
        if (recordsBeans != null){
            recordsBeans = null;
        }
    }
}
