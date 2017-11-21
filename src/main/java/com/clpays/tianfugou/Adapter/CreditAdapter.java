package com.clpays.tianfugou.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.clpays.tianfugou.Adapter.superAdapter.SuperAdapter;
import com.clpays.tianfugou.Adapter.superAdapter.SuperViewHolder;
import com.clpays.tianfugou.Entity.Credit.CreditType;
import com.clpays.tianfugou.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Name: CreditAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-11-14 18:00
 */

public class CreditAdapter extends SuperAdapter<CreditType> {
    List<CreditType> list;

    public CreditAdapter(Context context, List<CreditType> list, int layoutResId) {
        super(context, list, layoutResId);
        this.list=list;
    }

    @Override
    public SuperViewHolder onCreate(View convertView, ViewGroup parent, int viewType) {
        // These code show how to add click listener to item view of ViewHolder.
        final SuperViewHolder holder = super.onCreate(convertView, parent, viewType);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), ((TextView) (holder.findViewById(R.id.tv_name))).getText(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/
        return holder;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, CreditType item) {
        holder.setText(R.id.title, item.getTitle());
        holder.setImageResource(R.id.imageView,R.drawable.daikuan_icon);
    }

}
