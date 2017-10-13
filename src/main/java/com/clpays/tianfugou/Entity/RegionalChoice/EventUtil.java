package com.clpays.tianfugou.Entity.RegionalChoice;

/**
 * Name: EventUtil
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //基本资料地区弹窗选择专用
 * Date: 2017-10-12 12:48
 */

public class EventUtil {
    private String address;//区域

    public String getAddress2() {
        return address2;
    }

    private String address2;//详细地址

    public String getType() {
        return type;
    }

    private String type;

    public EventUtil(String type, String address,String address2) {
        this.address = address;
        this.address2=address2;
        this.type = type;
    }

    public String getAddress(){
        return this.address;
    }
}
