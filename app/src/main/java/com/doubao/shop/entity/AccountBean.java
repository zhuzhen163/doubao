package com.doubao.shop.entity;

import java.io.Serializable;

/**
 * Created by zhuzhen on 2018/11/24.
 */
public class AccountBean extends BaseEntity implements Serializable{
    String unPaymentNum;
    String deliveredNum;
    String code;
    String data;
    String successOrderNum;
    String cancelOrderNum;

    MemberInfo memberInfo;

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public String getUnPaymentNum() {
        return unPaymentNum;
    }

    public void setUnPaymentNum(String unPaymentNum) {
        this.unPaymentNum = unPaymentNum;
    }

    public String getDeliveredNum() {
        return deliveredNum;
    }

    public void setDeliveredNum(String deliveredNum) {
        this.deliveredNum = deliveredNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSuccessOrderNum() {
        return successOrderNum;
    }

    public void setSuccessOrderNum(String successOrderNum) {
        this.successOrderNum = successOrderNum;
    }

    public String getCancelOrderNum() {
        return cancelOrderNum;
    }

    public void setCancelOrderNum(String cancelOrderNum) {
        this.cancelOrderNum = cancelOrderNum;
    }

    public class MemberInfo{
        String is_vip;
        String is_vplus;
        String vipGrade;
        String vplusGrade;
        String members_growth_value;

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public String getIs_vplus() {
            return is_vplus;
        }

        public void setIs_vplus(String is_vplus) {
            this.is_vplus = is_vplus;
        }

        public String getVipGrade() {
            return vipGrade;
        }

        public void setVipGrade(String vipGrade) {
            this.vipGrade = vipGrade;
        }

        public String getVplusGrade() {
            return vplusGrade;
        }

        public void setVplusGrade(String vplusGrade) {
            this.vplusGrade = vplusGrade;
        }

        public String getMembers_growth_value() {
            return members_growth_value;
        }

        public void setMembers_growth_value(String members_growth_value) {
            this.members_growth_value = members_growth_value;
        }
    }
}
