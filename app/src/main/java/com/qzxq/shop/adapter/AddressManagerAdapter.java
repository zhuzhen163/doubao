package com.qzxq.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.ListBaseAdapter;
import com.qzxq.shop.base.SuperViewHolder;
import com.qzxq.shop.entity.AddressManagerEntity;

/**
* @author zhuzhen
* create at 2018/11/20
* description:
*/
public class AddressManagerAdapter extends ListBaseAdapter<AddressManagerEntity>{

    TextView tv_name,tv_phone,tv_address;
    ImageView iv_default_pic,iv_delete;
    public AddressManagerAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_address_manager;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_phone = holder.getView(R.id.tv_phone);
        iv_default_pic = holder.getView(R.id.iv_default_pic);
        tv_address = holder.getView(R.id.tv_address);
        iv_delete = holder.getView(R.id.iv_delete);

        AddressManagerEntity entity = mDataList.get(position);
        tv_name.setText(entity.getName());
        tv_phone.setText(entity.getPhone());
        tv_address.setText(entity.getAddress());
        if ("0".equals(entity.getDefaultAddress())){
            iv_default_pic.setVisibility(View.VISIBLE);
        }else {
            iv_default_pic.setVisibility(View.GONE);
        }
    }
}
