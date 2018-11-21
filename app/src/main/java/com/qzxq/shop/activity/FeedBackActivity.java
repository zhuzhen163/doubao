package com.qzxq.shop.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.BaseActivity;
import com.qzxq.shop.presenter.FeedBackActivityPresenter;
import com.qzxq.shop.tools.SwitchActivityManager;
import com.qzxq.shop.tools.ToastUtil;

import java.lang.reflect.Field;

import butterknife.BindView;

/**
* @author zhuzhen
* create at 2018/11/20
* description: 意见反馈
*/
public class FeedBackActivity extends BaseActivity <FeedBackActivityPresenter> implements NumberPicker.Formatter{

    @BindView(R.id.et_input)
    EditText et_input;
    @BindView(R.id.tv_textNum)
    TextView tv_textNum;
    @BindView(R.id.ll_kind)
    LinearLayout ll_kind;
    @BindView(R.id.tv_inputKind)
    TextView tv_inputKind;
    private String kind;

    private PopupWindow popupWindow;
    private NumberPicker mNumberPicker;
    private String[] mCities  = {"请选择反馈类型","商品相关","物流状况","客服服务","优惠活动","产品建议","其他"};
    @Override
    protected FeedBackActivityPresenter loadPresenter() {
        return new FeedBackActivityPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        ll_kind.setOnClickListener(this);
        setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.exitActivity(FeedBackActivity.this);
            }
        });
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tv_textNum.setText(charSequence.length()+"/500");
                if (charSequence.length()>500){
                    et_input.setText(charSequence.toString().substring(0,500));
                    et_input.setSelection(500);
                    ToastUtil.showLong("输入字数达到上限");
                    tv_textNum.setText(500+"/500");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initView() {
        setTitleName("意见反馈");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()){
            case R.id.ll_kind:
                showAddressPickerPop();
                break;
        }
    }


    /**
     * 显示地址选择的pop
     */
    private void showAddressPickerPop() {
        popupWindow = new PopupWindow(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_feed_back_kind, null, false);

        mNumberPicker = (NumberPicker)rootView.findViewById(R.id.number_picker);
        TextView tv_confirm = (TextView) rootView.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) rootView.findViewById(R.id.tv_cancel);
        TextView tv_cancel1 = (TextView) rootView.findViewById(R.id.tv_cancel1);
        mNumberPicker.setDisplayedValues(mCities);//设置需要显示的数组
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(mCities.length - 1);//这两行不能缺少,不然只能显示第一个，关联到format方法
        mNumberPicker.setWrapSelectorWheel(false);
        setPickerDividerColor();
        setNumberPickerTextColor(mNumberPicker, Color.BLACK);

        popupWindow.setContentView(rootView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.address_picker_anim);
        popupWindow.showAsDropDown(ll_kind);

        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                kind = mCities[mNumberPicker.getValue()];
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                tv_inputKind.setText(kind);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        tv_cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    //这个方法是根据index 格式化先生的文字,需要先 implements NumberPicker.Formatter
    @Override
    public String format(int value) {
        return mCities[value];
    }

    /**
     * 通过反射改变分割线颜色,
     */
    private void setPickerDividerColor() {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try{
                    pf.set(mNumberPicker,new ColorDrawable(getResources().getColor(R.color.colorLine)));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 过反射改变文字的颜色
     * @param numberPicker
     * @param color
     * @return
     */
    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popupWindow != null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }else {
                SwitchActivityManager.exitActivity(FeedBackActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
