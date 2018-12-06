package com.doubao.shop.entity;

/**
* @author zhuzhen
* create at 2018/11/23
* description:
*/
public class BaseEntity {

    private String errno;
    private String errmsg;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
