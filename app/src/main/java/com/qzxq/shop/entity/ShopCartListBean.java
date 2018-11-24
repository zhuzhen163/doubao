package com.qzxq.shop.entity;

import java.util.List;

/**
* @author zhuzhen
* create at 2018/11/23
* description:
*/
public class ShopCartListBean extends BaseEntity{

    ShopCartList data;

    public ShopCartList getData() {
        return data;
    }

    public void setData(ShopCartList data) {
        this.data = data;
    }

    public class ShopCartList{
        CartTotal cartTotal;
        List<CouponInfoList> couponInfoList;
        List<CartListBean> cartList;

        public CartTotal getCartTotal() {
            return cartTotal;
        }

        public void setCartTotal(CartTotal cartTotal) {
            this.cartTotal = cartTotal;
        }

        public List<CouponInfoList> getCouponInfoList() {
            return couponInfoList;
        }

        public void setCouponInfoList(List<CouponInfoList> couponInfoList) {
            this.couponInfoList = couponInfoList;
        }

        public List<CartListBean> getCartList() {
            return cartList;
        }

        public void setCartList(List<CartListBean> cartList) {
            this.cartList = cartList;
        }
    }



    public class CouponInfoList{
        private String msg;
        private String type;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public class CartTotal {
        private String goodsCount;
        private String checkedGoodsCount;
        private String goodsAmount;
        private String checkedGoodsAmount;

        public String getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getCheckedGoodsCount() {
            return checkedGoodsCount;
        }

        public void setCheckedGoodsCount(String checkedGoodsCount) {
            this.checkedGoodsCount = checkedGoodsCount;
        }

        public String getGoodsAmount() {
            return goodsAmount;
        }

        public void setGoodsAmount(String goodsAmount) {
            this.goodsAmount = goodsAmount;
        }

        public String getCheckedGoodsAmount() {
            return checkedGoodsAmount;
        }

        public void setCheckedGoodsAmount(String checkedGoodsAmount) {
            this.checkedGoodsAmount = checkedGoodsAmount;
        }
    }
}
