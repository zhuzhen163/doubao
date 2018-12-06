package com.doubao.shop.entity;


import java.util.List;

/**
 * Created by zhuzhen on 2018/11/23.
 */
public class AddressManagerBean extends BaseEntity{

    List<AddressDetailBean> data;

    public List<AddressDetailBean> getData() {
        return data;
    }

    public void setData(List<AddressDetailBean> data) {
        this.data = data;
    }
}
