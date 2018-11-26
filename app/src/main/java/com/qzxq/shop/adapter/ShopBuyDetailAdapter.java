package com.qzxq.shop.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.ListBaseAdapter;
import com.qzxq.shop.base.SuperViewHolder;
import com.qzxq.shop.entity.CartListBean;
import com.qzxq.shop.tools.AppUtils;

/**
* @author zhuzhen
* create at 2018/11/26
* description:
*/
public class ShopBuyDetailAdapter extends ListBaseAdapter<CartListBean> {

    CheckBox cb_select;
    ImageView iv_productImg;
    TextView tv_productName;
    TextView tv_productPrice;
    TextView tv_productNum;

    public ShopBuyDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_shop_buy_detail;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

        final CartListBean entity = mDataList.get(position);
        cb_select = holder.getView(R.id.cb_select);
        iv_productImg = holder.getView(R.id.iv_productImg);
        tv_productName = holder.getView(R.id.tv_productName);
        tv_productPrice = holder.getView(R.id.tv_productPrice);
        tv_productNum = holder.getView(R.id.tv_productNum);

        AppUtils.setImage(mContext,entity.getList_pic_url(),iv_productImg);
        tv_productName.setText(entity.getGoods_name());
        tv_productPrice.setText(entity.getMarket_price());
        tv_productNum.setText(entity.getNumber());
    }
}
