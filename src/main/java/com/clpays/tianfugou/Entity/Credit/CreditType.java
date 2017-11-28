package com.clpays.tianfugou.Entity.Credit;

import java.io.Serializable;

/**
 * Name: CreditType
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-11-14 18:01
 */

public class CreditType implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String id;
    String img;
    String title;
    String content;

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    String need;
    String pic;//介绍用

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    String cmd;
}
