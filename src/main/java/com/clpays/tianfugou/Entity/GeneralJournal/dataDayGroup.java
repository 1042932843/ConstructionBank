package com.clpays.tianfugou.Entity.GeneralJournal;

import java.util.List;

/**
 * Name: dataDayGroup
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-13 14:58
 */

public class dataDayGroup {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<dataDay> getList() {
        return list;
    }

    public void setList(List<dataDay> list) {
        this.list = list;
    }

    String date;
    String total;
    List<dataDay> list;
}
