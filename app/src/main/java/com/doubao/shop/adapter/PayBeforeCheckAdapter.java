package com.doubao.shop.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.ListBaseAdapter;
import com.doubao.shop.base.SuperViewHolder;
import com.doubao.shop.entity.CartListBean;
import com.doubao.shop.tools.AppUtils;

/**
* @author zhuzhen
* create at 2019/1/21
* description:
*/
public class PayBeforeCheckAdapter extends ListBaseAdapter<CartListBean> {

    ImageView iv_payBeforeImg;
    TextView tv_payBeforeName,tv_payBeforePrice,tv_payBeforeNum;

    public PayBeforeCheckAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_pay_before_check;
    }


    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        iv_payBeforeImg = holder.getView(R.id.iv_payBeforeImg);
        tv_payBeforeName = holder.getView(R.id.tv_payBeforeName);
        tv_payBeforePrice = holder.getView(R.id.tv_payBeforePrice);
        tv_payBeforeNum = holder.getView(R.id.tv_payBeforeNum);
        final CartListBean entity = mDataList.get(position);

        AppUtils.setImage(mContext,entity.getList_pic_url(),iv_payBeforeImg);
        tv_payBeforeName.setText(entity.getName());
        tv_payBeforePrice.setText("￥"+entity.getMarket_price());
        tv_payBeforeNum.setText(entity.getCart_num());
    }

}
