package com.doubao.shop.widget.addressselector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doubao.shop.R;

import java.util.List;


public class SelectAdapter extends BaseAdapter{
    List<AddressBean.ChangeRecordsBean> datas;
    int selectedIndex = Selector.INDEX_INVALID;
    public SelectAdapter(List<AddressBean.ChangeRecordsBean> datas) {
        this.datas = datas;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(datas.get(position).getThird_code());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

            holder = new Holder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        AddressBean.ChangeRecordsBean item = (AddressBean.ChangeRecordsBean) getItem(position);
        holder.textView.setText(item.getName());

        return convertView;
    }
    class Holder {
        TextView textView;
    }
}
