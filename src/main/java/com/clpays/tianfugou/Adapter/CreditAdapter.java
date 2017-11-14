package com.clpays.tianfugou.Adapter;

import android.content.Context;

import com.clpays.tianfugou.Adapter.superAdapter.SuperAdapter;
import com.clpays.tianfugou.Adapter.superAdapter.SuperViewHolder;
import com.clpays.tianfugou.Entity.Credit.CreditType;

import java.util.List;

/**
 * Name: CreditAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-11-14 18:00
 */

public class CreditAdapter extends SuperAdapter<CreditType> {
    public CreditAdapter(Context context, List<CreditType> list, int layoutResId) {
        super(context, list, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, CreditType item) {
        //holder.setText(R.id.tv_name, item);
    }

}
