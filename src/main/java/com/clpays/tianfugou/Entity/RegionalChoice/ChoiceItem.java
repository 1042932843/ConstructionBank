package com.clpays.tianfugou.Entity.RegionalChoice;

/**
 * Name: ChoiceItem
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //暂时没用
 * Date: 2017-10-11 16:19
 */

public class ChoiceItem {
   public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public boolean choice;
}
