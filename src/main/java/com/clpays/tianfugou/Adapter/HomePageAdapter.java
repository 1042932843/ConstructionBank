package com.clpays.tianfugou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Entity.HomePage.homeItem;
import com.clpays.tianfugou.Module.Major.Credit.CreditActivity;
import com.clpays.tianfugou.Module.Major.Home.FunctionTipActivity;
import com.clpays.tianfugou.Module.QRGathering.QRgatheringActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.UserState;


/**
 * Name: HomePageAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //首页功能选择的adapter
 * Date: 2017-09-11 17:14
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {
    List<homeItem> mDatas=new ArrayList<>();
    Context context;
    Intent it=new Intent();

    public HomePageAdapter(Context context, List<homeItem> list) {
        this.context=context;
        this.mDatas.clear();
        this.mDatas.addAll(list);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(context).inflate(R.layout.home_page_item, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        String name =mDatas.get(position).getName();
        holder.name.setText(name);
        Glide.with(context).load(mDatas.get(position).getImg()).apply(app.optionsNormalCrop).into(holder.img);
        holder.img.setOnClickListener(v -> {
            String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
                switch (name){
                    case "二维码收款":
                        if("finish".equals(s)){
                            it.setClass(context, QRgatheringActivity.class);
                            context.startActivity(it);
                        }else{
                            it.setClass(context, FunctionTipActivity.class);
                            it.putExtra("Title",name);
                            context.startActivity(it);
                        }

                        break;
                    case "银行贷款":
                        if("finish".equals(s)){
                            ToastUtil.ShortToast("功能维护中，请期待后续更新");
                            //it.setClass(context, QRgatheringActivity.class);
                            //context.startActivity(it);

                        }else{
                            it.setClass(context, CreditActivity.class);
                            it.putExtra("Title",name);
                            context.startActivity(it);
                        }
                        break;
                    case "商城缴费":
                        if("finish".equals(s)){
                            ToastUtil.ShortToast("功能维护中，请期待后续更新");
                        }else{
                            it.setClass(context, FunctionTipActivity.class);
                            it.putExtra("Title",name);
                            context.startActivity(it);
                        }
                        break;
            }

        });
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView name;
        ImageView img;

        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}
