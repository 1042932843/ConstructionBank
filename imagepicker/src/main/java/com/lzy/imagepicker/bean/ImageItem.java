package com.lzy.imagepicker.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：图片信息
 * 修订历史：
 * ================================================
 */
public class ImageItem implements Serializable, Parcelable {

    public String name="";       //图片的名字
    public String path="";       //图片的路径
    public long size=0;         //图片的大小
    public int width=0;         //图片的宽度
    public int height=0;        //图片的高度
    public String mimeType="";   //图片的类型
    public long addTime=0;      //图片的创建时间
    public String type="";//图片作用
    public String pushok="";
    public int id=-1;
    public int isUpload=-1;
    public String comment="";

    /** 图片的路径和创建时间相同就认为是同一张图片 */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ImageItem) {
            ImageItem item = (ImageItem) o;
            return this.path.equalsIgnoreCase(item.path) && this.addTime == item.addTime;
        }

        return super.equals(o);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.mimeType);
        dest.writeString(this.pushok);
        dest.writeString(this.type);
        dest.writeLong(this.addTime);
        dest.writeInt(this.id);
        dest.writeInt(this.isUpload);
        dest.writeString(this.comment);

    }

    public ImageItem() {
    }

    //这里和write的顺序，要保持一致，否则报错！！！
    protected ImageItem(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.width = in.readInt();
        this.height = in.readInt();
        this.mimeType = in.readString();
        this.pushok=in.readString();
        this.type = in.readString();
        this.addTime = in.readLong();
        this.id=in.readInt();
        this.isUpload=in.readInt();
        this.comment=in.readString();
    }

    public static final Parcelable.Creator<ImageItem> CREATOR = new Parcelable.Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
