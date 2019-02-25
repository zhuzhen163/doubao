package com.doubao.shop.entity;

import java.io.Serializable;

/**
 * Created by zhuzhen on 2018/11/24.
 */
public class AddressDetailBean implements Serializable{
        private String id;
        private String userId;
        private String userName;
        private String telNumber;
        private String full_region;
        private String isDefault;
        private String detailInfo;
        private String province = "";
        private String city = "";
        private String county = "";
        private String provinceName ;
        private String cityName ;
        private String countyName;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTelNumber() {
            return telNumber;
        }

        public void setTelNumber(String telNumber) {
            this.telNumber = telNumber;
        }

        public String getFull_region() {
            return full_region;
        }

        public void setFull_region(String full_region) {
            this.full_region = full_region;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }
}
