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
import nbsix.com.constructionbank.Entity.HomePage.homePageItem;
import nbsix.com.constructionbank.R;


/**
 * Name: HomePageAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-11 17:14
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {
    List<homePageItem> mDatas=new ArrayList<>();
    Context context;

    public HomePageAdapter(Context context, List<homePageItem> list) {
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
        holder.name.setText(mDatas.get(position).getName());
        Glide.with(context).load(mDatas.get(position).getImg()).apply(app.optionsRoundedCorners).into(holder.img);
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
