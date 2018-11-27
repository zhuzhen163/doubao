package com.qzxq.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.ListBaseAdapter;
import com.qzxq.shop.base.SuperViewHolder;
import com.qzxq.shop.entity.CartListBean;
import com.qzxq.shop.tools.AppUtils;
import com.qzxq.shop.widget.AdderView;

/**
 * Created by zhuzhen on 2018/11/19.
 */
public class ShopCartFragmentAdapter extends ListBaseAdapter<CartListBean> {

    CheckBox cb_select;
    ImageView iv_productImg;
    TextView tv_productName;
    TextView tv_productPrice;
    TextView tv_productNum;
    CheckBoxCallback checkBoxCallback;
    AdderView addView;
    private boolean isDelete;

    public void setCheckBoxCallback(CheckBoxCallback checkBoxCallback) {
        this.checkBoxCallback = checkBoxCallback;
    }

    public interface CheckBoxCallback{
        void checkAll(boolean checkAll);//是否全选购买
        void checkAllDelete(boolean checkAllDelete);//是否全选删除

        void isCheckShop(String isChecked,String productIds);//是否勾选商品
        void isCheckDelete(String isChecked,String productIds);//是否勾选删除

        void productNum(int number, String goods_id, String id, String product_id);//购物车商品数量变更
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
            CartListBean entity = mDataList.get(i);
            if (!entity.isCheck()) {
                check = false;
                return check;
            }
        }
        return check;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

        final CartListBean entity = mDataList.get(position);
        cb_select = holder.getView(R.id.cb_select);
        iv_productImg = holder.getView(R.id.iv_productImg);
        tv_productName = holder.getView(R.id.tv_productName);
        tv_productPrice = holder.getView(R.id.tv_productPrice);
        tv_productNum = holder.getView(R.id.tv_productNum);
        addView = holder.getView(R.id.addView);

        AppUtils.setImage(mContext,entity.getList_pic_url(),iv_productImg);
        tv_productName.setText(entity.getGoods_name());
        tv_productPrice.setText(entity.getMarket_price());
        tv_productNum.setText("*"+entity.getNumber());

        if ("1".equals(entity.getChecked())){
            cb_select.setChecked(true);
            entity.setCheck(true);
        }else {
            cb_select.setChecked(false);
            entity.setCheck(false);
        }

        if (isDelete){
            cb_select.setChecked(entity.isCheckDelete());
            tv_productName.setVisibility(View.GONE);
            tv_productNum.setVisibility(View.GONE);
            addView.setVisibility(View.VISIBLE);
        }else {
            cb_select.setChecked(entity.isCheck());
            tv_productName.setVisibility(View.VISIBLE);
            tv_productNum.setVisibility(View.VISIBLE);
            addView.setVisibility(View.GONE);
        }


        cb_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDelete){
                    if (entity.isCheckDelete()){
                        cb_select.setChecked(false);
                        entity.setCheckDelete(false);
                        checkBoxCallback.isCheckDelete("0",entity.getProduct_id());
                    }else {
                        cb_select.setChecked(true);
                        entity.setCheckDelete(true);
                        checkBoxCallback.isCheckDelete("1",entity.getProduct_id());
                    }
                    checkBoxCallback.checkAllDelete(isCheckAllDelete());
                    notifyDataSetChanged();
                }else {
                    if (entity.isCheck()){
                        cb_select.setChecked(false);
                        entity.setCheck(false);
                        checkBoxCallback.isCheckShop("0",entity.getProduct_id());
                    }else {
                        cb_select.setChecked(true);
                        entity.setCheck(true);
                        checkBoxCallback.isCheckShop("1",entity.getProduct_id());
                    }
                    checkBoxCallback.checkAll(isCheckAll());
                }
            }
        });

        addView.setValue(Integer.parseInt(entity.getNumber()));
        addView.setOnValueChangeListene(new AdderView.OnValueChangeListener() {
            @Override
            public void onValueChange(int value) {
                if (1 != value){
                    checkBoxCallback.productNum(value,entity.getGoods_id(),entity.getId(),entity.getProduct_id());
                }
            }
        });
    }


    public boolean isCheckAllDelete(){
        boolean check = true;
        for (int i = 0; i < mDataList.size(); i++) {
            CartListBean entity = mDataList.get(i);
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
