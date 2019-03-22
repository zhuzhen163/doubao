package com.doubao.shop.entity;

/**
* @author zhuzhen
* create at 2018/12/13
* description: 
*/
public class SubmitOrderBean {

    private String payurl;

    private String payCode;

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayurl() {
        return payurl;
    }

    public void setPayurl(String payurl) {
        this.payurl = payurl;
    }
}
