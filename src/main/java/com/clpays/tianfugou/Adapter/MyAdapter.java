package com.clpays.tianfugou.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.clpays.tianfugou.R;

import java.util.List;

/**
 * Name: MyAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-11-16 17:43
 */

public class MyAdapter implements SpinnerAdapter {
    List<String> list;
    Context context;

    public MyAdapter(List<String> list, Context context) {
        this.list=list;
        this.context=context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_regionalchoice_item, null);
        }
        holder.content = (TextView)convertView.findViewById(R.id.content);
        holder.content.setText(list.get(position));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_regionalchoice_item, null);
        }
        holder.content = (TextView)convertView.findViewById(R.id.content);
        holder.content.setText(list.get(position));
        return convertView;
    }

    class ViewHolder
    {
        TextView content;
    }
}
