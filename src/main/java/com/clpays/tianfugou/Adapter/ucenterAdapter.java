package com.clpays.tianfugou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Module.Major.AccountInfo.AccountInfoActivity;
import com.clpays.tianfugou.Module.Major.Authentication.AuthenticationInfoActivity;
import com.clpays.tianfugou.Module.Major.Authentication.StartAuthenticationActivity;
import com.clpays.tianfugou.Module.Major.Credit.MyCreditActivity;

import java.util.ArrayList;
import java.util.List;

import com.clpays.tianfugou.Entity.UCenter.ucItem;

import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.UserState;

/**
 * Name: ucenterAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //用户中心功能集合adapter
 * Date: 2017-09-14 18:35
 */

public class ucenterAdapter extends RecyclerView.Adapter<ucenterAdapter.MyViewHolder> {
    List<ucItem> ucItemList=new ArrayList<>();
    Context context;
    Intent it=new Intent();
    public ucenterAdapter(Context c, List<ucItem> list) {
        context=c;
        if(list!=null){
            ucItemList=list;
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view=LayoutInflater
                .from(context)
                .inflate(R.layout.ucenter_item
                        ,parent,
                        false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide
                .with(context)
                .load(ucItemList.get(position).getImg())
                .apply(app.optionsNormal)
                .into(holder.img);
        String type=ucItemList.get(position).getType();
        holder.type.setText(type);
        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case "我的账户":
                        ToastUtil.ShortToast("功能暂未开放，请期待后续版本更新");
                        //it.setClass(context, AccountInfoActivity.class);
                        //context.startActivity(it);
                        break;
                    case "认证信息":
                        String s= PreferenceUtil.getStringPRIVATE("status",UserState.NA);
                        switch (s){
                            case "waiting":
                            case "checked":
                            case "prepared":
                                it.setClass(context, StartAuthenticationActivity.class);
                                context.startActivity(it);
                                break;
                            default:
                                it.setClass(context, AuthenticationInfoActivity.class);
                                context.startActivity(it);
                                break;
                        }

                        break;
                    case "我的贷款":
                        it.setClass(context, MyCreditActivity.class);
                        context.startActivity(it);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ucItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView type;
        RelativeLayout list_item;
        public MyViewHolder(View view)
        {
            super(view);
            img=(ImageView) view.findViewById(R.id.img);
            type=(TextView) view.findViewById(R.id.type);
            list_item=(RelativeLayout)view.findViewById(R.id.list_item);
        }
    }
}
