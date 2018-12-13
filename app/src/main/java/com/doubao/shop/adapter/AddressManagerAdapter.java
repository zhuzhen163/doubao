package com.doubao.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doubao.shop.R;
import com.doubao.shop.base.ListBaseAdapter;
import com.doubao.shop.base.SuperViewHolder;
import com.doubao.shop.entity.AddressDetailBean;
import com.doubao.shop.tools.SwitchActivityManager;

/**
* @author zhuzhen
* create at 2018/11/20
* description:
*/
public class AddressManagerAdapter extends ListBaseAdapter<AddressDetailBean>{

    TextView tv_name,tv_phone,tv_address,tv_default;
    ImageView iv_delete,iv_change;
    RelativeLayout rl_item;
    public AdapterCallBack adapterCallBack;
    private String type;
    public AddressManagerAdapter(Context context) {
        super(context);
    }

    public interface AdapterCallBack{
        void deletePosition(int position,String id);
        void clickItem(String addressId);
    }

    public void setAdapterCallBack(AdapterCallBack adapterCallBack) {
        this.adapterCallBack = adapterCallBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_address_manager;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        rl_item = holder.getView(R.id.rl_item);
        tv_name = holder.getView(R.id.tv_name);
        tv_phone = holder.getView(R.id.tv_phone);
        tv_default = holder.getView(R.id.tv_default);
        tv_address = holder.getView(R.id.tv_address);
        iv_delete = holder.getView(R.id.iv_delete);
        iv_change = holder.getView(R.id.iv_change);

        final AddressDetailBean bean = mDataList.get(position);
        tv_name.setText(bean.getUserName());
        tv_phone.setText(bean.getTelNumber());
        tv_address.setText(bean.getFull_region());
        if ("1".equals(bean.getIsDefault())){
            tv_default.setVisibility(View.VISIBLE);
        }else {
            tv_default.setVisibility(View.GONE);
        }
        if ("1".equals(type)){//购物车修改地址
            iv_change.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.GONE);
        }else {
            iv_change.setVisibility(View.GONE);
            iv_delete.setVisibility(View.VISIBLE);
        }
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallBack.deletePosition(position,bean.getId());
            }
        });
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallBack.clickItem(bean.getId());
            }
        });
        iv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivityManager.startCreateAddressActivity(mContext,bean.getId());
            }
        });

    }

    public void setType(String type){
        this.type = type;
    }
}
