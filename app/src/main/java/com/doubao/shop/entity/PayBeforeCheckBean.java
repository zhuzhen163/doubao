package com.doubao.shop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhuzhen
 */
public class PayBeforeCheckBean extends BaseEntity implements Serializable{

    List<CartListBean> unsells;
    String isSell;

    public String getIsSell() {
        return isSell;
    }

    public void setIsSell(String isSell) {
        this.isSell = isSell;
    }

    public List<CartListBean> getUnsells() {
        return unsells;
    }

    public void setUnsells(List<CartListBean> unsells) {
        this.unsells = unsells;
    }
}
