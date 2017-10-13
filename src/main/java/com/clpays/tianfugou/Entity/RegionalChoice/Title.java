package com.clpays.tianfugou.Entity.RegionalChoice;

import java.util.List;

/**
 * Name: Title
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //暂时没用
 * Date: 2017-10-11 16:19
 */

public class Title {
   public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChoiceItem> getItems() {
        return items;
    }

    public void setItems(List<ChoiceItem> items) {
        this.items = items;
    }

    public List<ChoiceItem> items;
}
