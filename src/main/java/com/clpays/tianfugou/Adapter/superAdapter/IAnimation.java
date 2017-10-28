package com.clpays.tianfugou.Adapter.superAdapter;

import android.support.v7.widget.RecyclerView;

import com.clpays.tianfugou.Utils.Animation.BaseAnimation;


interface IAnimation {

    void enableLoadAnimation();

    void enableLoadAnimation(long duration, BaseAnimation animation);

    void cancelLoadAnimation();

    void setOnlyOnce(boolean onlyOnce);

    void addLoadAnimation(RecyclerView.ViewHolder holder);

}
