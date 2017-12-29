package com.clpays.tianfugou.Entity.Pay;

import java.io.Serializable;

/**
 * Name: payItem
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-12-27 15:53
 */

public class payItem implements Serializable {
    int id;
    String type;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    String comment;
    String month;
    String fee;
    boolean paid;
}
