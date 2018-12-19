package com.doubao.shop.widget.addressselector;

import java.util.List;

/**
* @author zhuzhen
* create at 2018/12/19
* description: 
*/
public class AddressBean {


    List<ChangeRecordsBean> data;

    public List<ChangeRecordsBean> getData() {
        return data;
    }

    public void setData(List<ChangeRecordsBean> data) {
        this.data = data;
    }

    public class ChangeRecordsBean {
        /*** id*/
        public int id;
        /*** 地址编号*/
        public String third_code;
        /*** 中文名*/
        public String name;
        /*** 父id*/
        public int parent_id;

        public String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThird_code() {
            return third_code;
        }

        public void setThird_code(String third_code) {
            this.third_code = third_code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }
    }
}
