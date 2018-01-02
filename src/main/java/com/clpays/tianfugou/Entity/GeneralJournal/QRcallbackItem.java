package com.clpays.tianfugou.Entity.GeneralJournal;

/**
 * Name: QRcallbackItem
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-12-29 17:29
 */

public class QRcallbackItem {
    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    String cardnumber;
    String amount;
    String ts;
}
