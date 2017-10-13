package com.clpays.tianfugou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.clpays.tianfugou.Entity.Credentials.CredentialsItem;
import com.clpays.tianfugou.R;


/**
 * Name: CredentialsAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-11 17:14
 */

public class CredentialsAdapter extends RecyclerView.Adapter<CredentialsAdapter.MyViewHolder> {
    List<CredentialsItem> mDatas=new ArrayList<>();
    Context context;
    Intent it=new Intent();

    public CredentialsAdapter(Context context, List<CredentialsItem> list) {
        this.context=context;
        this.mDatas.clear();
        this.mDatas.addAll(list);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(context).inflate(R.layout.credentials_item, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        String name =mDatas.get(position).getName();
        holder.name.setText(name);
        String value=mDatas.get(position).getValue();
        holder.value.setText(value);

    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView name;
        TextView value;

        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            value = (TextView) view.findViewById(R.id.value);
        }
    }
}
