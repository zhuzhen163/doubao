package com.qzxq.shop.entity;

/**
 * 购物车
 */
public class ShopCartEntity {

    private boolean isCheck;//购物是否勾选标识
    private boolean isCheckDelete;//删除是否勾选标识
    private String productId;
    private String productImg;
    private String productName;
    private String productPrice;
    private String productNum;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean isCheckDelete() {
        return isCheckDelete;
    }

    public void setCheckDelete(boolean checkDelete) {
        isCheckDelete = checkDelete;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
}
