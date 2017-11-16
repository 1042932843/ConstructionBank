package com.clpays.tianfugou.Entity.Auth;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: AddressBean
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-11-16 16:28
 */

public class AddressBean implements Parcelable,Serializable{
    String address;

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel in) {
            return new AddressBean(in);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    ArrayList<String> items;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeList(items);
    }

    public AddressBean() {
    }

    //这里和write的顺序，要保持一致，否则报错！！！
    protected AddressBean(Parcel in) {
        this.address = in.readString();
        this.items = in.readArrayList(String.class.getClassLoader());

    }

}
