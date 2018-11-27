package com.qzxq.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzxq.shop.R;
import com.qzxq.shop.base.ListBaseAdapter;
import com.qzxq.shop.base.SuperViewHolder;
import com.qzxq.shop.entity.AddressDetailBean;

/**
* @author zhuzhen
* create at 2018/11/20
* description:
*/
public class AddressManagerAdapter extends ListBaseAdapter<AddressDetailBean>{

    TextView tv_name,tv_phone,tv_address;
    ImageView iv_default_pic,iv_delete;
    public DeleteCallBack deleteCallBack;
    public AddressManagerAdapter(Context context) {
        super(context);
    }

    public interface DeleteCallBack{
        void deletePosition(int position,String id);
    }

    public void setDeleteCallBack(DeleteCallBack deleteCallBack) {
        this.deleteCallBack = deleteCallBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_address_manager;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_phone = holder.getView(R.id.tv_phone);
        iv_default_pic = holder.getView(R.id.iv_default_pic);
        tv_address = holder.getView(R.id.tv_address);
        iv_delete = holder.getView(R.id.iv_delete);

        final AddressDetailBean bean = mDataList.get(position);
        tv_name.setText(bean.getUserName());
        tv_phone.setText(bean.getTelNumber());
        tv_address.setText(bean.getFull_region());
        if ("1".equals(bean.getIsDefault())){
            iv_default_pic.setVisibility(View.VISIBLE);
        }else {
            iv_default_pic.setVisibility(View.GONE);
        }
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCallBack.deletePosition(position,bean.getId());
            }
        });
    }
}
