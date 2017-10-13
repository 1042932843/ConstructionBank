package com.clpays.tianfugou.Entity.PackageChoice;

import java.util.ArrayList;

/**第一级实体bean
 * Created by Administrator on 2016/7/15.
 */
public class FirstBean {
    private String title;
    ArrayList<SecondBean> firstData = new ArrayList<SecondBean>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<SecondBean> getFirstData() {
        return firstData;
    }

    public void setFirstData(ArrayList<SecondBean> firstData) {
        this.firstData = firstData;
    }
}
