package com.clpays.tianfugou.Adapter.superAdapter;


public abstract class SimpleMulItemViewType<T> implements IMulItemViewType<T> {

    @Override
    public int getViewTypeCount() {
        return 1;
    }

}
