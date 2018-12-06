package com.doubao.shop.entity;

/**
 * Created by zhuzhen on 2018/11/23.
 * 购物车商品
 */
public class CartListBean {
//    private boolean isCheck;//购物是否勾选标识
    private boolean isCheckDelete;//删除是否勾选标识
    private String id;
    private String user_id;
    private String session_id;
    private String goods_id;
    private String goods_sn;
    private String product_id;
    private String goods_name;
    private String market_price;
    private String retail_price;
    private String retail_product_price;
    private String number;
    private String checked;//购物是否勾选标识
    private String crash_save_price;
    private String list_pic_url;
    private String good_url;

//    public boolean isCheck() {
//        return isCheck;
//    }
//
//    public void setCheck(boolean check) {
//        isCheck = check;
//    }

    public boolean isCheckDelete() {
        return isCheckDelete;
    }

    public void setCheckDelete(boolean checkDelete) {
        isCheckDelete = checkDelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public String getRetail_product_price() {
        return retail_product_price;
    }

    public void setRetail_product_price(String retail_product_price) {
        this.retail_product_price = retail_product_price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getCrash_save_price() {
        return crash_save_price;
    }

    public void setCrash_save_price(String crash_save_price) {
        this.crash_save_price = crash_save_price;
    }

    public String getList_pic_url() {
        return list_pic_url;
    }

    public void setList_pic_url(String list_pic_url) {
        this.list_pic_url = list_pic_url;
    }

    public String getGood_url() {
        return good_url;
    }

    public void setGood_url(String good_url) {
        this.good_url = good_url;
    }
}
