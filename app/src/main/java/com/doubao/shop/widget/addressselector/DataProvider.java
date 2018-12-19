package com.doubao.shop.widget.addressselector;

import java.util.List;

public interface DataProvider {
    void provideData(int currentDeep, int preId, DataReceiver receiver);


    interface DataReceiver {
        void send(List<AddressBean.ChangeRecordsBean> data);
    }
}
