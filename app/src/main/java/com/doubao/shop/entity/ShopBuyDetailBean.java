package com.doubao.shop.entity;

import java.util.List;

/**
 * Created by zhuzhen on 2018/11/26.
 * 购买详情实体
 */
public class ShopBuyDetailBean extends BaseEntity {

    private ShopBuy data;

    public ShopBuy getData() {
        return data;
    }

    public void setData(ShopBuy data) {
        this.data = data;
    }

    public class ShopBuy{
        private AddressDetailBean checkedAddress;
        private List<CartListBean> checkedGoodsList;
        private String actualPrice;
        private String orderTotalPrice;
        private String couponPrice;
        private String freightPrice;
        private String goodsTotalPrice;
        private String disCountAmount;
        private String userAmount;
        private String residueAmount;
        private String usableAmount;
        private boolean ampleAmountFlag;
        private String vipDisCountAmount;
        private String memberType;

        public String getVipDisCountAmount() {
            return vipDisCountAmount;
        }

        public void setVipDisCountAmount(String vipDisCountAmount) {
            this.vipDisCountAmount = vipDisCountAmount;
        }

        public String getMemberType() {
            return memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }

        public String getUsableAmount() {
            return usableAmount;
        }

        public void setUsableAmount(String usableAmount) {
            this.usableAmount = usableAmount;
        }

        public String getUserAmount() {
            return userAmount;
        }

        public void setUserAmount(String userAmount) {
            this.userAmount = userAmount;
        }

        public String getResidueAmount() {
            return residueAmount;
        }

        public void setResidueAmount(String residueAmount) {
            this.residueAmount = residueAmount;
        }

        public boolean isAmpleAmountFlag() {
            return ampleAmountFlag;
        }

        public void setAmpleAmountFlag(boolean ampleAmountFlag) {
            this.ampleAmountFlag = ampleAmountFlag;
        }

        public String getDisCountAmount() {
            return disCountAmount;
        }

        public void setDisCountAmount(String disCountAmount) {
            this.disCountAmount = disCountAmount;
        }

        public AddressDetailBean getCheckedAddress() {
            return checkedAddress;
        }

        public void setCheckedAddress(AddressDetailBean checkedAddress) {
            this.checkedAddress = checkedAddress;
        }

        public List<CartListBean> getCheckedGoodsList() {
            return checkedGoodsList;
        }

        public void setCheckedGoodsList(List<CartListBean> checkedGoodsList) {
            this.checkedGoodsList = checkedGoodsList;
        }

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getOrderTotalPrice() {
            return orderTotalPrice;
        }

        public void setOrderTotalPrice(String orderTotalPrice) {
            this.orderTotalPrice = orderTotalPrice;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getFreightPrice() {
            return freightPrice;
        }

        public void setFreightPrice(String freightPrice) {
            this.freightPrice = freightPrice;
        }

        public String getGoodsTotalPrice() {
            return goodsTotalPrice;
        }

        public void setGoodsTotalPrice(String goodsTotalPrice) {
            this.goodsTotalPrice = goodsTotalPrice;
        }
    }

}
