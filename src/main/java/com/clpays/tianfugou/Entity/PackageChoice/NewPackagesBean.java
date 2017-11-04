package com.clpays.tianfugou.Entity.PackageChoice;

import android.content.ClipData;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: NewPackagesBean
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-10-31 16:10
 */

public class NewPackagesBean {
    String title;
    boolean isChoice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    String related;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    public List<ThirdBean> getBeenList() {
        return beenList;
    }

    public void setBeenList(List<ThirdBean> beenList) {
        this.beenList = beenList;
    }

    List<ThirdBean> beenList=new ArrayList<>();
}
