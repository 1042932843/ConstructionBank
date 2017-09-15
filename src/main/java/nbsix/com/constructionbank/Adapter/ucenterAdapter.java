package nbsix.com.constructionbank.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nbsix.com.constructionbank.App.app;
import nbsix.com.constructionbank.Entity.UCenter.ucItem;
import nbsix.com.constructionbank.R;

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

    public ucenterAdapter(Context c, List<ucItem> list) {
        context=c;
        if(list!=null){
            ucItemList=list;
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.ucenter_item
                        ,parent,
                        false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide
                .with(context)
                .load(ucItemList.get(position).getImg())
                .apply(app.optionsNormal)
                .into(holder.img);
        holder.type.setText(ucItemList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return ucItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView type;
        public MyViewHolder(View view)
        {
            super(view);
            img=(ImageView) view.findViewById(R.id.img);
            type=(TextView) view.findViewById(R.id.type);
        }
    }
}
