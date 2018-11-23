package com.qzxq.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.ListBaseAdapter;
import com.qzxq.shop.base.SuperViewHolder;
import com.qzxq.shop.entity.ShopCartEntity;
import com.qzxq.shop.tools.AppUtils;

/**
 * Created by zhuzhen on 2018/11/19.
 */
public class ShopCartFragmentAdapter extends ListBaseAdapter<ShopCartEntity> {

    CheckBox cb_select;
    ImageView iv_productImg;
    TextView tv_productName;
    TextView tv_productPrice;
    TextView tv_productNum;
    CheckBoxCallback checkBoxCallback;
    private boolean isDelete;

    public void setCheckBoxCallback(CheckBoxCallback checkBoxCallback) {
        this.checkBoxCallback = checkBoxCallback;
    }

    public interface CheckBoxCallback{
        void checkAll(boolean checkAll);
        void checkBoxCallback(boolean checkAllDelete);
    }


    public ShopCartFragmentAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_shop_cart;
    }

    public boolean isCheckAll(){
        boolean check = true;
        for (int i = 0; i < mDataList.size(); i++) {
            ShopCartEntity entity = mDataList.get(i);
            if (!entity.isCheck()) {
                check = false;
                return check;
            }
        }
        return check;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

        final ShopCartEntity entity = mDataList.get(position);
        cb_select = holder.getView(R.id.cb_select);
        iv_productImg = holder.getView(R.id.iv_productImg);
        tv_productName = holder.getView(R.id.tv_productName);
        tv_productPrice = holder.getView(R.id.tv_productPrice);
        tv_productNum = holder.getView(R.id.tv_productNum);

        AppUtils.setImage(mContext,entity.getProductImg(),iv_productImg);
        tv_productName.setText(entity.getProductName());
        tv_productPrice.setText(entity.getProductPrice());
        tv_productNum.setText(entity.getProductNum());

        if (isDelete){
            cb_select.setChecked(entity.isCheckDelete());
        }else {
            cb_select.setChecked(entity.isCheck());
        }

        cb_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDelete){
                    if (entity.isCheckDelete()){
                        cb_select.setChecked(false);
                        entity.setCheckDelete(false);
                    }else {
                        cb_select.setChecked(true);
                        entity.setCheckDelete(true);
                    }
                    checkBoxCallback.checkBoxCallback(isCheckAllDelete());
                }else {
                    if (entity.isCheck()){
                        cb_select.setChecked(false);
                        entity.setCheck(false);
                    }else {
                        cb_select.setChecked(true);
                        entity.setCheck(true);
                    }
                    checkBoxCallback.checkAll(isCheckAll());
                }
                notifyDataSetChanged();
            }
        });
    }


    public boolean isCheckAllDelete(){
        boolean check = true;
        for (int i = 0; i < mDataList.size(); i++) {
            ShopCartEntity entity = mDataList.get(i);
            if (!entity.isCheckDelete()) {
                check = false;
                return check;
            }
        }
        return check;
    }

    public void isDelete(boolean isDelete){
        this.isDelete = isDelete;
    }
}
